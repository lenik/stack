package com.bee32.sem.event.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

@Entity
public class EventCategory
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public EventCategory() {
        super();
    }

    public EventCategory(String name, String alias) {
        super(name, alias);
    }

    public EventCategory(String name, String alias, String description) {
        super(name, alias, description);
    }

}
