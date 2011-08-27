package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Eauto<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    K id;

    {
        autoId = true;
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
