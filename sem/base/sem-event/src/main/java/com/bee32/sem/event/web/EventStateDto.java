package com.bee32.sem.event.web;

import com.bee32.plover.orm.ext.dict.DictEntityDto;
import com.bee32.sem.event.entity.EventState;

public class EventStateDto
        extends DictEntityDto<EventState, Integer> {

    private static final long serialVersionUID = 1L;

    public EventStateDto() {
        super();
    }

    public EventStateDto(EventState source) {
        super(source);
    }

}
