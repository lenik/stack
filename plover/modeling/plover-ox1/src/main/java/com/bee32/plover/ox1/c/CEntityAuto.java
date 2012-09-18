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
        this(null);
    }

    public CEntityAuto(String name) {
        super(name);
        autoId = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof CEntityAuto)
            _populate((CEntityAuto<K>) source);
        else
            super.populate(source);
    }

    protected void _populate(CEntityAuto <K>o) {
        super._populate(o);
        // this. = o.;
    }

    /**
     * 标识符
     *
     * 记录的标识符、主键。
     */
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
