package com.bee32.sem.event.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.dto.EventDto;
import com.bee32.sem.event.entity.Event;

@RequestMapping(EventController.PREFIX + "**")
public class EventController
        extends AbstractEventController<Event, EventDto> {

    public static final String PREFIX = SEMEventModule.PREFIX + "/event";

}
