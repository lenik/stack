package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 */
@Entity
@DiscriminatorValue("TSK")
public class Task
        extends Event
        implements ITask {

    private static final long serialVersionUID = 1L;

    private TaskPriority taskPriority;

    @ManyToOne
    @Override
    public TaskPriority getPriority() {
        return taskPriority;
    }

    public void setPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

}
