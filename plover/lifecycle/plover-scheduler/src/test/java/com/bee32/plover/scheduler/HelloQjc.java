package com.bee32.plover.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.scheduler.quartz.AbstractQuartzJobConfigurer;
import com.bee32.plover.scheduler.util.Jobs;
import com.bee32.plover.scheduler.util.Triggers;

public class HelloQjc
        extends AbstractQuartzJobConfigurer {

    static Logger logger = LoggerFactory.getLogger(HelloQjc.class);

    @Override
    public void load(SchedulerFactory schedulerFactory)
            throws SchedulerException {
        Scheduler sched = schedulerFactory.getScheduler();

        JobDetail job1 = Jobs.declare("hello", HelloJob.class);
        SimpleTriggerImpl trigger = Triggers.timeout("timeout", 0);

        sched.scheduleJob(job1, trigger);
    }

}
