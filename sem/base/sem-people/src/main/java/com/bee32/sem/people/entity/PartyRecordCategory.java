package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

@Entity
public class PartyRecordCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PartyRecordCategory() {
        super();
    }

    public PartyRecordCategory(String name, String label) {
        super(name, label);
    }

    public PartyRecordCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
