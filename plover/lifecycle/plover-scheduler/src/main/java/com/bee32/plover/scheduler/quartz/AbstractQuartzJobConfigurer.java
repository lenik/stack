package com.bee32.plover.scheduler.quartz;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

//@ComponentTemplate
public abstract class AbstractQuartzJobConfigurer
        implements IQuartzJobConfigurer {

    @Override
    public void load(SchedulerFactory schedulerFactory)
            throws SchedulerException {
    }

    @Override
    public void unload(SchedulerFactory schedulerFactory)
            throws SchedulerException {
    }

}
