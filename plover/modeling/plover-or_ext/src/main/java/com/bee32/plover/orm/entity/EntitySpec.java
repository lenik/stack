package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.ext.CEntity;

@MappedSuperclass
public abstract class EntitySpec<K extends Serializable>
        extends CEntity<K> {

    private static final long serialVersionUID = 1L;

    public EntitySpec() {
        super();
    }

    public EntitySpec(String name) {
        super(name);
    }

}
