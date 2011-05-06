package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class ContactCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ContactCategory() {
        super();
    }

    public ContactCategory(String name, String label) {
        super(name, label);
    }

    public ContactCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
