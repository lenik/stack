package com.bee32.plover.orm.entity;

import java.io.Serializable;

public abstract class EntityExp<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    @Override
    protected abstract void setId(K id);

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        EntityExp<K> o = (EntityExp<K>) other;
        return equalsKey(o);
    }

    /**
     * Natural id equality.
     *
     * @return <code>null</code> If natural id is unknown.
     */
    protected Boolean equalsKey(EntityExp<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((EntityExp<K>) otherEntity);
    }

    /**
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean equalsEntity(EntityExp<K> otherEntity) {
        return false;
    }

}
