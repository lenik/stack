package com.bee32.sem.event.entity;

import java.util.Date;

public interface ITask
        extends IEvent {

    TaskPriority getPriority();

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
