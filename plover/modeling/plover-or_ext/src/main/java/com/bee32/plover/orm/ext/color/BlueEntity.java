package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;

/**
 * <b>blue</b>
 * <p>
 * <i>description</i> — Is it simply a catalog-like description which classifies or 'labels' an
 * object? If users of a system are labeled based on the department of a company they work within
 * and this doesn't change the way the system behaves, this would be a description.
 */
@MappedSuperclass
public abstract class BlueEntity<K extends Serializable>
        extends EntityAuto<K> {

    private static final long serialVersionUID = 1L;

    public BlueEntity() {
        super();
    }

    public BlueEntity(String name) {
        super(name);
    }

    @Override
    protected Boolean naturalEquals(EntityBase<K> other) {
        String name = getName();
        String otherName = other.getName();
        if (name == null || otherName == null)
            return false;

        if (!name.equals(otherName))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        String name = getName();

        if (name == null)
            return 0;
        else
            return name.hashCode();
    }

}
