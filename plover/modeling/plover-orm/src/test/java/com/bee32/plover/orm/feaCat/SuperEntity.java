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

X-Population

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
