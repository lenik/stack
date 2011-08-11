package com.bee32.plover.scheduler.quartz;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
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
