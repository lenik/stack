package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
@MappedSuperclass
@_AutoId
// @SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "xxx_seq")
public abstract class EntityAuto<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    K id;

    public EntityAuto() {
        this(null);
    }

    public EntityAuto(String name) {
        super(name);
        autoId = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof EntityAuto)
            _populate((EntityAuto<K>) source);
        else
            super.populate(source);
    }

    protected void _populate(EntityAuto<K> o) {
        super._populate(o);
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
