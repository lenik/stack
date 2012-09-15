package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 采购原则
 *
 * 产品采购原则字典类。
 *
 */
@Entity
public class PurchaseRegulation
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public PurchaseRegulation() {
        super();
    }

    public PurchaseRegulation(String name, String label, String description) {
        super(name, label, description);
    }

    public PurchaseRegulation(String name, String label) {
        super(name, label);
    }

}
