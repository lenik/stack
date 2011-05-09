package com.bee32.sem.event.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;
import com.bee32.plover.orm.util.TransferBy;
import com.bee32.sem.event.dto.EventCategoryDto;

@Entity
@TransferBy(EventCategoryDto.class)
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
