package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 洽谈类型
 */
@Entity
public class ChanceActionStyle
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceActionStyle() {
        super();
    }

    public ChanceActionStyle(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceActionStyle(String name, String label) {
        super(name, label);
    }



}
