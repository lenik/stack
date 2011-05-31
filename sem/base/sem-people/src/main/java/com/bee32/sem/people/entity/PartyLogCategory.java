package com.bee32.sem.people.entity;

import com.bee32.plover.orm.ext.dict.NameDict;

public class PartyLogCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PartyLogCategory() {
        super();
    }

    public PartyLogCategory(String name, String label) {
        super(name, label);
    }

    public PartyLogCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
