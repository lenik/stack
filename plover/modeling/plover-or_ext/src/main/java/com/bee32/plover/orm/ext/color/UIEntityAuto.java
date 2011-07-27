package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity._AutoId;

@MappedSuperclass
@_AutoId
public abstract class UIEntityAuto<K extends Serializable>
        extends UIEntity<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public UIEntityAuto() {
        super();
    }

    public UIEntityAuto(String name) {
        super(name);
    }

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
