package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import overlay.Overlay;

import com.bee32.plover.arch.Component;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * You may annotate the concrete entities with:
 * <ul>
 * <li>&#64;Entity
 * <li>&#64;Table
 * </ul>
 */
@MappedSuperclass
public abstract class Entity<K extends Serializable>
        extends EntityBase<K>
        implements IMultiFormat {

    private static final long serialVersionUID = 1L;

    int version;

    public Entity() {
        super(null);
    }

    public Entity(String name) {
        super(name);
    }

    abstract void setId(K id);

    // @Version
    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void populate(Object source) {
    }

    @Override
    public Entity<K> detach() {
        return this;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null)
            return false;

        Class<?> class1 = getClass();
        Class<?> class2 = obj.getClass();
        if (class1 != class2) {
            if (!class1.isAssignableFrom(class2)) {
                if (class2.isAssignableFrom(class1))
                    return obj.equals(this);
                else
                    return false;
            }
            // assert class1.isAssignableFrom(class2);
        }

        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        return equalsSpecific(other);
    }

    @Override
    protected final boolean equalsSpecific(Component obj) {
        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        Boolean keyEq = equalsKey(other);
        if (keyEq != null)
            return keyEq;

        return equalsEntity(other);
    }

    protected Boolean equalsKey(Entity<K> other) {
        K id1 = getId();
        K id2 = other.getId();

        if (id1 == null && id2 == null)
            return null;

        if (id1 == null || id2 == null)
            return Boolean.FALSE;

        if (!id1.equals(id2))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    /**
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean equalsEntity(Entity<K> other) {
        return this == other;
    }

    @Override
    public final int hashCode() {
        return typeHash + hashCodeEntity();
    }

    /**
     * Not used.
     */
    @Override
    protected final int hashCodeSpecific() {
        int hash = 0xbabade33 * version;

        K id = getId();
        if (id != null)
            hash += id.hashCode();
        else
            hash += hashCodeEntity();

        return hash;
    }

    protected int hashCodeEntity() {
        K id = getId();

        if (id == null)
            return System.identityHashCode(this);
        else
            return id.hashCode();
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
    public void toString(final PrettyPrintStream out, final FormatStyle format) {
        toString(out, format, null, 0);
    }

    @Overlay
    public void toString(final PrettyPrintStream out, final FormatStyle format, final Set<Object> occurred,
            final int depth) {
        new EntityFormatter(out, occurred).format(this, format, depth);
    }

    protected <T extends Entity<K>> T cast(Class<T> thatClass, Entity<K> thatLike) {
        if (thatLike == null)
            return null;

        return thatClass.cast(thatLike);
    }

}
