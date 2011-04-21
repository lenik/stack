package com.bee32.sem.event.web;

import com.bee32.plover.orm.ext.dict.DictDto;
import com.bee32.sem.event.entity.EventState;

public class EventStateDto
        extends DictDto<EventState, Integer> {

    private static final long serialVersionUID = 1L;

    public EventStateDto() {
        super();
    }

    public EventStateDto(EventState source) {
        super(source);
    }

}
