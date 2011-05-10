package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <b>blue</b>
 * <p>
 * <i>description</i> — Is it simply a catalog-like description which classifies or 'labels' an
 * object? If users of a system are labeled based on the department of a company they work within
 * and this doesn't change the way the system behaves, this would be a description.
 */
@MappedSuperclass
public abstract class BlueEntity<K extends Serializable>
        extends BlueEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public BlueEntity() {
        super();
    }

    public BlueEntity(String name) {
        super(name);
    }

    @Id
    @GeneratedValue
    @Override
    public K getId() {
        return id;
    }

    @Override
    protected void setId(K id) {
        this.id = id;
    }

}
