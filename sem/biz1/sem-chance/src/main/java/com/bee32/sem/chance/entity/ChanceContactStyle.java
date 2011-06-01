package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

public class ChanceContactStyle
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceContactStyle() {
        super();
    }

    public ChanceContactStyle(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceContactStyle(String name, String label) {
        super(name, label);
    }



}
