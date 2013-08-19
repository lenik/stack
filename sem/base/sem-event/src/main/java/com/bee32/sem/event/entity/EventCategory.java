package com.bee32.sem.event.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 事件分类
 *
 * <p lang="en">
 * Event Category
 */
@Entity
public class EventCategory
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public EventCategory() {
        super();
    }

    public EventCategory(String name, String label) {
        super(name, label);
    }

    public EventCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
