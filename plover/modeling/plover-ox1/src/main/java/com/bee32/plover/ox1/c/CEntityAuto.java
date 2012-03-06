package com.bee32.plover.ox1.c;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity._AutoId;

@MappedSuperclass
@_AutoId
// @SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "xxx_seq")
public abstract class CEntityAuto<K extends Serializable>
        extends CEntity<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public CEntityAuto() {
        super();
    }

    public CEntityAuto(String name) {
        super(name);
    }

    {
        autoId = true;
    }

X-Population

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    @Override
    public K getId() {
        return id;
    }

    @Override
    protected void setId(K id) {
        this.id = id;
    }

}
