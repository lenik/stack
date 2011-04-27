package com.bee32.sem.event.dto;

import com.bee32.plover.orm.ext.dict.DictEntityDto;
import com.bee32.sem.event.entity.EventCategory;

public class EventCategoryDto
        extends DictEntityDto<EventCategory, Integer> {

    private static final long serialVersionUID = 1L;

    public EventCategoryDto() {
        super();
    }

    public EventCategoryDto(EventCategory source) {
        super(source);
    }

}
