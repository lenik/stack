package com.bee32.sem.event.entity;

import java.util.Date;

public interface ITask
        extends IEvent {

    /**
     * Task category, it maybe from personal calendar program, or system generated tasks.
     *
     * @return Task category name.
     */
    @Override
    String getCategory();

    /**
     * Get the task priority.
     */
    @Override
    int getPriority();

    public static final String TASK_OPEN = "open";
    public static final String TASK_CLOSED = "closed";
    public static final String TASK_PENDING = "pending";
    public static final String TASK_DONE = EVENT_CLEARED;

    /**
     * Get the state of task.
     *
     * @return Task state name.
     */
    @Override
    String getState();

    /**
     * Get the scheduled start time.
     *
     * @return Non-<code>null</code> scheduled start time.
     */
    @Override
    public Date getBeginTime();

    /**
     * Get the scheduled end time.
     *
     * @return Non-<code>null</code> scheduled end time.
     */
    @Override
    public Date getEndTime();

}
