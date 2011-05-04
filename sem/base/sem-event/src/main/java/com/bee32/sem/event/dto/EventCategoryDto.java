package com.bee32.sem.event.dto;

import com.bee32.plover.orm.ext.dict.NameDictDto;
import com.bee32.sem.event.entity.EventCategory;

public class EventCategoryDto
        extends NameDictDto<EventCategory> {

    private static final long serialVersionUID = 1L;

    public EventCategoryDto() {
        super();
    }

    public EventCategoryDto(EventCategory source) {
        super(source);
    }

}
