package com.bee32.sem.event.dto;

import com.bee32.sem.event.entity.Activity;

public class ActivityDto
        extends AbstractEventDto<Activity> {

    private static final long serialVersionUID = 1L;

    public ActivityDto() {
        super();
    }

    public ActivityDto(Activity source) {
        super(source);
    }

}
