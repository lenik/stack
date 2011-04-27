package com.bee32.sem.event.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortDictEntity;

@Entity
public class EventCategory
        extends ShortDictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public EventCategory() {
        super();
    }

    public EventCategory(String name, String displayName, String icon) {
        super(name, displayName, icon);
    }

    public EventCategory(String name, String displayName) {
        super(name, displayName);
    }

}
