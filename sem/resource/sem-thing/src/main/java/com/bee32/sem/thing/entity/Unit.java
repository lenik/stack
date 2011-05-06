package com.bee32.sem.thing.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * [字典] 单位
 */
@Entity
public class Unit
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public Unit() {
        super();
    }

    public Unit(String name, String label) {
        super(name, label);
    }

    public Unit(String name, String label, String description) {
        super(name, label, description);
    }

}
