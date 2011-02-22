package com.bee32.plover.orm.entity;

public abstract class Entity<K>
        implements IEntity<K>, IPopulatable {

    private static final long serialVersionUID = 1L;

    private K primaryKey;

    @Override
    public K getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(K primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public void populate(Object obj) {
    }

    @Override
    public String toString() {
        return getPrimaryKey() + "@" + getClass().getName();
    }

}
