package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 机会来源
 *
 * 别人介绍，互联网，电话扫
 *
 */
@Entity
public class ChanceSourceType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceSourceType() {
        super();
    }

    public ChanceSourceType(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceSourceType(String name, String label) {
        super(name, label);
    }

}
