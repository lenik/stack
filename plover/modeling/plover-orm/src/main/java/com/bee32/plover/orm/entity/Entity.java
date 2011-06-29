package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import overlay.Overlay;

import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.util.EntityFormatter;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

@MappedSuperclass
public abstract class Entity<K extends Serializable>
        extends EntityBase<K>
        implements IMultiFormat {

    private static final long serialVersionUID = 1L;

    static final int KEYWORD_MAXLEN = 16;

    int version;

    Date createdDate = new Date();
    Date lastModified = createdDate;

    final EntityFlags entityFlags = new EntityFlags();

    String keyword;
    boolean keywordUpdated;

    int aclId;

    public Entity() {
        super(null);
    }

    public Entity(String name) {
        super(name);
    }

    protected abstract void setId(K id);

    // @Version
    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    void setCreatedDate(Date createdDate) {
        if (createdDate == null)
            throw new NullPointerException("createdDate");
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        if (lastModified == null)
            throw new NullPointerException("lastModified");
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

    @Index(name = "keyword")
    @Column(length = KEYWORD_MAXLEN)
    protected String getKeyword() {
        if (!keywordUpdated) {
            keyword = buildKeyword();
            keywordUpdated = true;
        }
        return keyword;
    }

    protected void setKeyword(String keyword) {
        this.keyword = keyword;
        this.keywordUpdated = true;
    }

    protected void invalidate() {
        invalidateKeyword();
    }

    protected final void invalidateKeyword() {
        this.keywordUpdated = false;
    }

    /**
     * @see ZhUtil#getPinyinAbbreviation(String).
     */
    @Transient
    protected String buildKeyword() {
        return null;
    }

    @Column(name = "acl", nullable = false)
    public int getAclId() {
        return aclId;
    }

    public void setAclId(int aclId) {
        this.aclId = aclId;
    }

    @Override
    public void populate(Object source) {
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

        Boolean naturalEquals = naturalEquals(other);
        if (naturalEquals != null)
            return naturalEquals;

        return contentEquals(other);
    }

    @Override
    protected final int hashCodeSpecific() {
        Integer idHash = naturalHashCode();
        if (idHash != null)
            return idHash;
        else
            return contentHashCode();
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
    protected Boolean naturalEquals(EntityBase<K> other) {
        return idEquals(other);
    }

    protected Integer naturalHashCode() {
        return idHashCode();
    }

    protected final boolean idEquals(EntityBase<K> other) {
        K id1 = getId();
        K id2 = other.getId();

        if (id1 == null || id2 == null)
            return false;

        return id1.equals(id2);
    }

    protected final int idHashCode() {
        K id = getId();
        if (id != null)
            return id.hashCode();
        return System.identityHashCode(this);
    }

    /**
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean contentEquals(EntityBase<K> other) {
        return this == other;
    }

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

}
