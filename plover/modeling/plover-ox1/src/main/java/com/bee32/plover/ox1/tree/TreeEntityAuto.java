package com.bee32.plover.ox1.tree;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity._AutoId;

@MappedSuperclass
@_AutoId
public abstract class TreeEntityAuto<K extends Serializable, self_t extends TreeEntityAuto<K, self_t>>
        extends TreeEntity<K, self_t> {

    private static final long serialVersionUID = 1L;

    K id;

    public TreeEntityAuto() {
        super();
    }

    public TreeEntityAuto(String name) {
        super(name);
    }

    public TreeEntityAuto(self_t parent) {
        super(parent);
    }

    public TreeEntityAuto(self_t parent, String name) {
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
