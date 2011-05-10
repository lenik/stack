package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * <b>yellow</b>
 * <p>
 * <i>roles</i> — Is it a way of participating in an activity (by either a person, place, or thing)?
 * Signing into a system as an administrator, which changes program behavior by requiring a password
 * that guest accounts do not, is one example.
 */
@MappedSuperclass
public abstract class YellowEntity<K extends Serializable>
        extends EntityAuto<K> {

    private static final long serialVersionUID = 1L;

    public YellowEntity() {
        super();
    }

    public YellowEntity(String name) {
        super(name);
    }

}
