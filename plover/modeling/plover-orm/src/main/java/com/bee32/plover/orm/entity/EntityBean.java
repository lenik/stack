package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityBean<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    protected K id;

    public EntityBean() {
        super();
    }

    public EntityBean(String name) {
        super(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public K getId() {
        return id;
    }

    void setId(K id) {
        this.id = id;
    }

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        return equalsKey((EntityBean<K>) other);
    }

    protected Boolean equalsKey(EntityBean<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((EntityBean<K>) otherEntity);
    }

    protected boolean equalsEntity(EntityBean<K> otherEntity) {
        return super.equalsEntity(otherEntity);
    }

}
