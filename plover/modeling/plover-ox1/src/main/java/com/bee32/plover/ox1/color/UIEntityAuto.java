package com.bee32.plover.ox1.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.orm.entity._AutoId;

@ModelTemplate
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
