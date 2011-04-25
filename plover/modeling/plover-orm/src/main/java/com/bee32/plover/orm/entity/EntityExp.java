package com.bee32.plover.orm.entity;

import java.io.Serializable;

public abstract class EntityExp<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    @Override
    protected abstract void setId(K id);

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((EntityExp<K>) otherEntity);
    }

    protected boolean equalsEntity(EntityExp<K> otherEntity) {
        return false;
    }

}
