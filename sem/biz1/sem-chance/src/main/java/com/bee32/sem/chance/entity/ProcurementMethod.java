package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 供货方式
 *
 * 产品供货方式字典类。
 */
@Entity
public class ProcurementMethod
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ProcurementMethod() {
        super();
    }

    public ProcurementMethod(String name, String label, String description) {
        super(name, label, description);
    }

    public ProcurementMethod(String name, String label) {
        super(name, label);
    }

}
