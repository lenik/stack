package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会来源
 */
@Entity
public class ChanceSource
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceSource() {
        super();
    }

    public ChanceSource(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceSource(String name, String label) {
        super(name, label);
    }

}
