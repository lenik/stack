package com.bee32.plover.orm.entity;

import java.io.Serializable;

public abstract class EntitySpec<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    public EntitySpec() {
        super();
    }

    public EntitySpec(String name) {
        super(name);
    }

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        EntitySpec<K> o = (EntitySpec<K>) other;
        return equalsKey(o);
    }

    /**
     * Natural id equality.
     *
     * @return <code>null</code> If natural id is unknown.
     */
    protected Boolean equalsKey(EntitySpec<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((EntitySpec<K>) otherEntity);
    }

    /**
     * @param other
     *            Non-<code>null</code> entity whose contents instead of the key need to be
     *            compared.
     */
    protected boolean equalsEntity(EntitySpec<K> otherEntity) {
        return false;
    }

}
