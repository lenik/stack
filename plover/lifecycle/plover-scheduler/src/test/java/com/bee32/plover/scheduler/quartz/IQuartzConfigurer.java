package com.bee32.plover.scheduler.quartz;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

public interface IQuartzConfigurer {

    void load(SchedulerFactory schedulerFactory)
            throws SchedulerException;

    void unload(SchedulerFactory schedulerFactory)
            throws SchedulerException;

}
