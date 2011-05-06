package com.bee32.sem.people.entity;

import com.bee32.plover.orm.ext.dict.NameDict;

public class PersonLogCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PersonLogCategory() {
        super();
    }

    public PersonLogCategory(String name, String label) {
        super(name, label);
    }

    public PersonLogCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
