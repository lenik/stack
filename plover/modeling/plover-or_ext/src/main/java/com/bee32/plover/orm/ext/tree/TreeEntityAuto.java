package com.bee32.plover.orm.ext.tree;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity._AutoId;

@MappedSuperclass
@_AutoId
public abstract class TreeEntityAuto<K extends Serializable, $ extends TreeEntityAuto<K, $>>
        extends TreeEntity<K, $> {

    private static final long serialVersionUID = 1L;

    K id;

    public TreeEntityAuto() {
        super();
    }

    public TreeEntityAuto(String name) {
        super(name);
    }

    public TreeEntityAuto($ parent, String name) {
        super(parent, name);
    }

    {
        autoId = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    @Override
    public K getId() {
        return id;
    }

    @Override
    protected void setId(K id) {
        if (id == null)
            throw new NullPointerException("id");
        this.id = id;
    }

}
