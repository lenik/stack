package com.bee32.plover.scheduler.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.PropertySettingJobFactory;

public abstract class QuartzPlayer {

    protected Scheduler sched;

    public void run()
            throws SchedulerException, InterruptedException {
        sched = StdSchedulerFactory.getDefaultScheduler();
        sched.setJobFactory(new PropertySettingJobFactory());

        sched.start();

        setup();

        Thread.sleep(1000000);

        sched.shutdown(true); // wait=false
    }

    protected abstract void setup()
            throws SchedulerException;

}
