package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

/**
 * <b>yellow</b>
 * <p>
 * <i>roles</i> — Is it a way of participating in an activity (by either a person, place, or thing)?
 * Signing into a system as an administrator, which changes program behavior by requiring a password
 * that guest accounts do not, is one example.
 */
@MappedSuperclass
public abstract class YellowEntity<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    protected K id;

    public YellowEntity() {
        super();
    }

    public YellowEntity(String name) {
        super(name);
    }

    @Id
    @GeneratedValue
    @Override
    public K getId() {
        return id;
    }

    protected void setId(K id) {
        this.id = id;
    }

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        return equalsKey((YellowEntity<K>) other);
    }

    protected Boolean equalsKey(YellowEntity<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((YellowEntity<K>) otherEntity);
    }

    protected boolean equalsEntity(YellowEntity<K> otherEntity) {
        return super.equalsEntity(otherEntity);
    }

}
