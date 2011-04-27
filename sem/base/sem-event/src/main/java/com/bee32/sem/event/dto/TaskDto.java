package com.bee32.sem.event.dto;

import com.bee32.sem.event.entity.Task;

public class TaskDto
        extends AbstractEventDto<Task> {

    private static final long serialVersionUID = 1L;

    public TaskDto() {
        super();
    }

    public TaskDto(Task source) {
        super(source);
    }

}
