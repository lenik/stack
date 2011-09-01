package com.bee32.sem.asset.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
public class Payment
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public Payment() {
        super();
    }

    public Payment(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    public Payment(int order, String name, String label) {
        super(order, name, label);
    }

    public Payment(String name, String label, String description) {
        super(name, label, description);
    }

    public Payment(String name, String label) {
        super(name, label);
    }

}
