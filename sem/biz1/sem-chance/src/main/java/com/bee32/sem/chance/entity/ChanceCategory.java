package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

public class ChanceCategory
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceCategory() {
        super();
    }

    public ChanceCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceCategory(String name, String label) {
        super(name, label);
    }

}
