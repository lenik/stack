package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import overlay.Overlay;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.bean.ReflectLocks;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Conjunction;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.sample.StandardSamples;
import com.bee32.plover.orm.util.EntityFormatter;
import com.bee32.plover.servlet.util.HttpAssembledContext;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

@ServiceTemplate
@MappedSuperclass
public abstract class Entity<K extends Serializable>
        extends EntityBase<K>
        implements Cloneable, IMultiFormat {

    private static final long serialVersionUID = 1L;

    public static final int ALT_ID_LENGTH = 20;

    int version;
    String altId;

    Date createdDate = new Date();
    Date lastModified = createdDate;

    final EntityFlags entityFlags = new EntityFlags();

    transient Entity<?> nextOfMicroLoop;
    transient Object declaringObject;
    transient Field declaringField;

    public Entity() {
        super();
        // prePersist();
    }

    public Entity(String name) {
        super(name);
        // prePersist();
    }

    @Overlay
    public Entity<K> clone() {
        Entity<K> copy;
        try {
            copy = getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        copy._retarget(this);
        copy.populate(this);
        return copy;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Entity<?>) {
            // Don't copy the identity properties.
            // _retarget((Entity<K>) source);
            _populate((Entity<?>) source);
        } else {
            // super.populate(source);
            throw new IllegalArgumentException("Unsupport populate source: " + source);
        }
    }

    public void _populate(Entity<?> o) {
        altId = o.altId;
    }

    public void retarget(Object o) {
        if (o instanceof Entity<?>) {
            @SuppressWarnings("unchecked")
            Entity<K> entity = (Entity<K>) o;
            _retarget(entity);
        } else {
            // super.populate(source);
            throw new IllegalArgumentException("Unsupport retarget target: " + o);
        }
    }

    final void _retarget(Entity<?> o) {
        if (o == null) {
            setId(null);
            version = 0;
            lastModified = createdDate = new Date();
        } else {
            K id = (K) o.getId();
            setId(id);
            version = o.version;
            createdDate = o.createdDate;
            lastModified = o.lastModified;
            entityFlags.bits = o.entityFlags.bits;
        }
    }

    protected static <E extends Entity<?>> E _retarget(E source, E target) {
        if (source == null)
            return target;
        source.retarget(target);
        return source;
    }

    protected static <E extends Entity<?>> void _retargetMerge(List<E> list, Iterable<E> targetItems) {
        for (E targetItem : targetItems) {
            if (!_retargetMerge(list, targetItem)) {
                list.add(targetItem);
            }
        }
    }

    protected static <E extends Entity<?>> boolean _retargetMerge(List<E> list, E targetItem) {
        if (targetItem == null)
            throw new NullPointerException("targetItem");
        int index = list.indexOf(targetItem);
        if (index != -1) {
            Entity<?> found = list.get(index);
            found.retarget(targetItem);
            return true;
        }
        return false;
    }

    protected static <E extends Entity<?>> void _retargetMerge(Set<E> set, Set<E> targetItems) {
        for (E targetItem : targetItems) {
            if (!_retargetMerge(set, targetItem)) {
                set.add(targetItem);
            }
        }
    }

    protected static <E extends Entity<?>> boolean _retargetMerge(Set<E> set, E targetItem) {
        if (targetItem == null)
            throw new NullPointerException("targetItem");
        for (E item : set) {
            if (item.equals(targetItem)) {
                item.retarget(targetItem);
                return true;
            }
        }
        return false;
    }

    protected abstract void setId(K id);

    // @Version
    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Column(length = ALT_ID_LENGTH)
    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    void setCreatedDate(Date createdDate) {
        if (createdDate == null) {
            // logger.warn
            createdDate = new Date(); // new NullPointerException("createdDate");
        }
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Index(name = "##_lastModified")
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        if (lastModified == null) {
            // logger.warn
            lastModified = new Date(); // throw new NullPointerException("lastModified");
        }
        this.lastModified = lastModified;
    }

    @Column(nullable = false)
    int getEf() {
        return entityFlags.bits;
    }

    void setEf(int eflags) {
        this.entityFlags.bits = eflags;
    }

    @Transient
    protected EntityFlags getEntityFlags() {
        return entityFlags;
    }

    @Override
    public Entity<K> detach() {
        return this;
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
        Entity<K> other = (Entity<K>) obj;

        return equalsSpecific(other);
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
        return hashCodeSpecific();
    }

    @Override
    protected final boolean equalsSpecific(Component obj) {
        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        Serializable nid = naturalId();
        if (nid == null)
            return idEquals(other);

        Serializable nidOther = other.naturalId();

        if (nidOther == null) {
            // logger.warning("Natural Id isn't defined on other entity: " + other);
            return false;
        }

        return nid.equals(nidOther);
    }

    @Override
    protected final int hashCodeSpecific() {
        Serializable nid = naturalId();
        if (nid == null)
            return idHashCode();

        else
            return nid.hashCode();
    }

    @Transient
    public final Serializable getNaturalId() {
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
     * @see IdComposite
     */
    protected Serializable naturalId() {
        if (altId != null)
            return altId;
        else
            return null;
    }

    protected static Serializable naturalId(Entity<?> entity) {
        if (entity == null)
            return null;
        else
            return entity.naturalId();
    }

    @Transient
    public final ICriteriaElement getSelector() {
        return selector("");
    }

    /**
     * Get the criteria construction for natural id selection.
     *
     * @param prefix
     *            Non-null property name prefix.
     * @return <code>null</code> for transient object (its natural-id is undetermined).
     */
    protected ICriteriaElement selector(String prefix) {
        if (altId != null)
            return new Equals(prefix + "altId", altId);

        K id = getId();
        if (id != null)
            return new Equals(prefix + "id", id);

        return null;
    }

    protected static ICriteriaElement selectors(ICriteriaElement... selectors) {
        if (selectors.length == 0)
            throw new IllegalArgumentException("selectors is empty");

        for (ICriteriaElement s : selectors)
            if (s == null)
                return null;

        switch (selectors.length) {
        case 1:
            return selectors[0];
        case 2:
        default:
            return new Conjunction(selectors);
        }
    }

    protected static ICriteriaElement selector(String property, Entity<?> entity) {
        return selector(property, entity, false);
    }

    protected static ICriteriaElement selector(String property, Entity<?> entity, boolean nullable) {
        if (property == null)
            throw new NullPointerException("property");

        if (entity == null)
            if (nullable)
                return new IsNull(property);
            else
                return null;

        String prefix = property + ".";

        ICriteriaElement selector = entity.selector(prefix);

        Serializable nid = entity.getNaturalId();
        if (nid == null)
            return selector;
        else
            return new CriteriaComposite(//
                    new Alias(property, property), //
                    selector);
    }

    protected boolean idEquals(EntityBase<K> other) {
        Entity<K> o = (Entity<K>) other;

        K id = getId();
        if (id == null)
            return other == null;

        K idOther = o.getId();
        return id.equals(idOther);
    }

    protected int idHashCode() {
        int hash = 0;
        K id = getId();
        if (id != null)
            hash += id.hashCode();
        return hash;
    }

    /**
     * By default, there is no content-equality, the same operator is used.
     *
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean contentEquals(EntityBase<K> other) {
        return this == other;
    }

    /**
     * By default, content hash code is the same as system identity.
     */
    protected int contentHashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public final String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public final String toString(FormatStyle format) {
        PrettyPrintStream out = new PrettyPrintStream();
        toString(out, format);
        return out.toString();
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        toString(out, format, null, 0);
    }

    @Overlay
    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        EntityFormatter formatter = new EntityFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

    protected <T extends Entity<K>> T cast(Class<T> thatClass, Entity<K> thatLike) {
        if (thatLike == null)
            return null;

        return thatClass.cast(thatLike);
    }

    @Transient
    public boolean isLocked() {
        return ReflectLocks.isLocked(this);
    }

    @Transient
    boolean isAnyLocked() {
        EntityFlags ef = EntityAccessor.getFlags(this);
        int lockFlags = ef.bits & (EntityFlags.USER_LOCK | EntityFlags.LOCKED);
        if (lockFlags != 0)
            return true;
        return isLocked();
    }

    @Transient
    Set<Entity<?>> getPrereqs() {
        @SuppressWarnings("unchecked")
        Set<Entity<?>> prereqs = (Set<Entity<?>>) (Object) new IdentityHashSet();
        fillPrereqs(prereqs);
        return prereqs;
    }

    protected void fillPrereqs(Set<Entity<?>> prereqs) {
    }

    protected static <S extends StandardSamples> S predefined(Class<? extends S> samplesClass) {
        S samples = HttpAssembledContext.bean.getBean(samplesClass);
        return samples;
    }

}
