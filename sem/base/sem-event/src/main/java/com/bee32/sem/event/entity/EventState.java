package com.bee32.sem.event.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortDictEntity;

@Entity
public class EventState
        extends ShortDictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public EventState() {
        super();
    }

    public EventState(String name, String description, String icon) {
        super(name, description, icon);
    }

}
