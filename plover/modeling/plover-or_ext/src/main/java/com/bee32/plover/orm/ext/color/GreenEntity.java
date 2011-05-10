package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GreenEntity<K extends Serializable>
        extends GreenEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public GreenEntity() {
        super();
    }

    public GreenEntity(String name) {
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
