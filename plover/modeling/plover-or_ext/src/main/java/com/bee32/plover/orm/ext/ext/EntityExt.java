package com.bee32.plover.orm.ext.ext;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityBean;

@MappedSuperclass
public class EntityExt<K extends Serializable>
        extends EntityBean<K> {

    private static final long serialVersionUID = 1L;

    public EntityExt() {
        super();
    }

    public EntityExt(String name) {
        super(name);
    }

}
