package com.bee32.plover.arch.util.dto;

import java.io.Serializable;
import java.lang.reflect.Modifier;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.Flags32;

public abstract class BaseDto<S, C>
        extends BaseDto_AS2<S, C>
        implements Cloneable {

    private static final long serialVersionUID = 1L;

    protected Class<? extends S> sourceType;
    protected final Flags32 selection = new Flags32();
    private boolean newCreated;

    /**
     * Full marshal by default.
     */
    public BaseDto() {
        this(F_MORE);
    }

    public BaseDto(int fmask) {
        this.selection.bits = fmask;

        // initSourceType(ClassUtil.<S> infer1(getClass(), BaseDto.class, 0));
        String dtoFqcn = getClass().getName();
        String siblingFqcn;
        String parentFqcn;
        String entityFqcn;

        if (dtoFqcn.endsWith("Dto") || dtoFqcn.endsWith("DTO"))
            siblingFqcn = dtoFqcn.substring(0, dtoFqcn.length() - 3);
        else
            siblingFqcn = dtoFqcn;

        entityFqcn = siblingFqcn.replace(".dto.", ".entity.");
        parentFqcn = siblingFqcn.replace(".dto.", ".");

        String[] tryFqcns = { siblingFqcn, parentFqcn, entityFqcn };

        Class<? extends S> srcType = null;
        for (String fqcn : tryFqcns) {
            try {
                srcType = (Class<? extends S>) Class.forName(fqcn);
            } catch (ClassNotFoundException e) {
                // try next.
            }
        }
        if (srcType == null)
            throw new IllegalUsageException(String.format(//
                    "Bad DTO name %s: can't determine source type.",//
                    dtoFqcn));
        initSourceType(srcType);
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * When it's a generic DTO, then the source type is not specialized yet. So there should be a
     * chance to change it.
     *
     * One should override {@link #initSourceType(Class)} as public so it may be changed by user.
     *
     * @param sourceType
     *            New source type.
     */
    @SuppressWarnings("unchecked")
    protected void initSourceType(Class<? extends S> sourceType) {
        // XXX cast?
        this.sourceType = (Class<S>) sourceType;
    }

    public <$ extends BaseDto<?, ?>> $ create() {
        int modifiers = sourceType.getModifiers();
        if (Modifier.isAbstract(modifiers))
            throw new IllegalUsageException("You can't create a DTO for abstract entity.");

        S newInstance;
        try {
            newInstance = sourceType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + sourceType, e);
        }
        newCreated = true;

        // Instead of:
        // __marshal(newInstance);
        // _marshal(newInstance);
        marshal(newInstance);
        stereotyped = false;

        @SuppressWarnings("unchecked")
        $ self = ($) this;
        return self;
    }

    public boolean isNewCreated() {
        return newCreated;
    }

    public int getSelection() {
        return selection.bits;
    }

    public void setSelection(int fmask) {
        this.selection.bits = fmask;
    }

    /**
     * 实体的等价关系按照下面的顺序定义：
     * <ul>
     * <li>1, 如果 a == b，则 a 与 b 等价。
     * <li>2, 如果 a 与 b 的自然等价关系有定义，则该自然等价关系作为实体的等价关系。
     * <li>3, 如果 a 与 b 的自然等价关系无定义，但 a 与 b 的内容相等，则 a 与 b 等价。
     * <li>3*, 第3条可以强化为：a 与 b 的内容相等，当且仅当 a 与 b 的非瞬态内容相等。
     * <li>3**, 第3条可以进一步强化为：a 与 b 的内容相等，当且仅当 a 与 b 的第一间接非瞬态内容相等。 </ol>
     * <p>
     * <b>自然等价</b>
     * <p>
     * 大多数情况下实体应该在其上定义自然键，并实现自然等价关系（通过实现方法 {@link #naturalEquals(EntityBase)} 和
     * {@link #naturalHashCode()}）。 自然键最好不是自动生成的 id 属性，但必须是唯一的。例如：用户的自然键应该是登录名，订单项的自然键应该是{所属的订单id,
     * 商品的id} 等等。
     *
     * <p>
     * <b>内容等价</b> 当无法在实体上定义自然键时，可以定义内容等价，通过实现方法 {@link #contentEquals(EntityBase)} 和
     * {@link #contentHashCode()}。
     *
     * 内容等价对两个实体的几乎全部内容进行比较，因而非常低效。您应该尽量避免使用基于内容的等价。
     *
     * <p>
     * <b>等价关系：equals 和 hashCode</b> 当您定义自然等价或内容等价时，必须同时实现对应的 *equals() 和 *hashCode() 两个方法，并且必须满足：
     * <ul>
     * <li>交换性：若 a.*equals(b) 则必有 b.*equals(a)。
     * <li>传递性：若 a.*equals(b) 并且 b.*equals(c) 则必有 a.*equals(c)。
     * <li>同调性：若 a.*equals(b)，则 a.*hashCode 必须和 b.*hashCode 相等。
     * </ul>
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        Class<?> thisClass = getClass();
        Class<?> otherClass = obj.getClass();
        if (thisClass != otherClass) {
            if (!thisClass.isAssignableFrom(otherClass)) {
                if (otherClass.isAssignableFrom(thisClass))
                    return obj.equals(this);
                else
                    return false;
            }
        }

        // assert thisClass < otherClass;

        @SuppressWarnings("unchecked")
        BaseDto<S, C> other = (BaseDto<S, C>) obj;

        Serializable nid = getNaturalId();
        if (nid == null)
            return idEquals(other);

        Serializable nidOther = other.getNaturalId();
        if (nidOther == null) {
            // logger.warn("Natural Id of the other DTO is null: " + other);
            return false;
        }
        return nid.equals(nidOther);
    }

    /**
     * 实体的等价关系按照下面的顺序定义：
     * <ul>
     * <li>1, 如果 a == b，则 a 与 b 等价。
     * <li>2, 如果 a 与 b 的自然等价关系有定义，则该自然等价关系作为实体的等价关系。
     * <li>3, 如果 a 与 b 的自然等价关系无定义，但 a 与 b 的内容相等，则 a 与 b 等价。
     * <li>3*, 第3条可以强化为：a 与 b 的内容相等，当且仅当 a 与 b 的非瞬态内容相等。
     * <li>3**, 第3条可以进一步强化为：a 与 b 的内容相等，当且仅当 a 与 b 的第一间接非瞬态内容相等。 </ol>
     * <p>
     * <b>自然等价</b>
     * <p>
     * 大多数情况下实体应该在其上定义自然键，并实现自然等价关系（通过实现方法 {@link #naturalEquals(EntityBase)} 和
     * {@link #naturalHashCode()}）。 自然键最好不是自动生成的 id 属性，但必须是唯一的。例如：用户的自然键应该是登录名，订单项的自然键应该是{所属的订单id,
     * 商品的id} 等等。
     *
     * <p>
     * <b>内容等价</b> 当无法在实体上定义自然键时，可以定义内容等价，通过实现方法 {@link #contentEquals(EntityBase)} 和
     * {@link #contentHashCode()}。
     *
     * 内容等价对两个实体的几乎全部内容进行比较，因而非常低效。您应该尽量避免使用基于内容的等价。
     *
     * <p>
     * <b>等价关系：equals 和 hashCode</b> 当您定义自然等价或内容等价时，必须同时实现对应的 *equals() 和 *hashCode() 两个方法，并且必须满足：
     * <ul>
     * <li>交换性：若 a.*equals(b) 则必有 b.*equals(a)。
     * <li>传递性：若 a.*equals(b) 并且 b.*equals(c) 则必有 a.*equals(c)。
     * <li>同调性：若 a.*equals(b)，则 a.*hashCode 必须和 b.*hashCode 相等。
     * </ul>
     */
    @Override
    public final int hashCode() {
        Serializable nid = getNaturalId();
        if (nid == null)
            return idHashCode();
        else
            return nid.hashCode();
    }

    protected final Serializable getNaturalId() {
        return naturalId();
    }

    /**
     * 判断两个实体是否自然等价。
     *
     * <p>
     * 如果实体上无法定义自然键，应该实现此方法并明确地返回 <code>null</code>。
     *
     * @return 返回 <code>true</code>或 <code>false</code> 表示自然键等价或不等价。返回<code>null</code>
     *         表示无法判定是否自然键等价。
     */
    protected Serializable naturalId() {
        return null;
    }

    protected static Serializable naturalId(BaseDto<?, ?> o) {
        if (o == null)
            return null;
        else
            return o.getNaturalId();
    }

    protected abstract boolean idEquals(BaseDto<S, C> other);

    protected abstract int idHashCode();

    /**
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean contentEquals(BaseDto<S, C> other) {
        return this == other;
    }

    protected int contentHashCode() {
        return System.identityHashCode(this);
    }

    @Override
    protected Object mergeDeref(S given) {
        if (given == null) {
            // create a new instance.
            S source;
            try {
                source = sourceType.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new MarshalException("Failed to create a new " + sourceType, e);
            }
            return source;
        } else {
            return given;
        }
    }

}
