package com.bee32.plover.orm.ext.c;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class CEntitySpec<K extends Serializable>
        extends CEntity<K> {

    private static final long serialVersionUID = 1L;

    public CEntitySpec() {
        super();
    }

    public CEntitySpec(String name) {
        super(name);
    }

}
