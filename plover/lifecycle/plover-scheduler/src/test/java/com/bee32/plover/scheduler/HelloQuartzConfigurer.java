package com.bee32.plover.scheduler;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import com.bee32.plover.scheduler.quartz.IQuartzConfigurer;

public class HelloQuartzConfigurer
        implements IQuartzConfigurer {

    @Override
    public void load(SchedulerFactory schedulerFactory)
            throws SchedulerException {
        Scheduler sched = schedulerFactory.getScheduler();
    }

    @Override
    public void unload(SchedulerFactory schedulerFactory)
            throws SchedulerException {
        schedulerFactory.getScheduler();
    }

}
