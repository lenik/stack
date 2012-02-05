package com.bee32.sem.event.dto;

import com.bee32.sem.event.entity.Event;

public class EventDto
        extends AbstractEventDto<Event> {

    private static final long serialVersionUID = 1L;

    public EventDto() {
        super();
    }

    public EventDto(int fmask) {
        super(fmask);
    }

}
