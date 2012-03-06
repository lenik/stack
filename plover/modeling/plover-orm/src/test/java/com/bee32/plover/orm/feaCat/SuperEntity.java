package com.bee32.plover.orm.feaCat;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

@MappedSuperclass
public abstract class SuperEntity<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public SuperEntity() {
        super();
    }

    public SuperEntity(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof SuperEntity)
            _populate((SuperEntity<K>) source);
        else
            super.populate(source);
    }

    protected void _populate(SuperEntity<K> o) {
        super._populate(o);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    @Override
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

}
