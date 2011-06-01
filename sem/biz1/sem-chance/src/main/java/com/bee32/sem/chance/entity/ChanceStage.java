package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

public class ChanceStage
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceStage() {
        super();
    }

    public ChanceStage(String name, String label) {
        super(name, label);
    }

    public ChanceStage(String name, String label, String description) {
        super(name, label, description);
    }

}
