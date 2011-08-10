package com.bee32.plover.scheduler.fp;

import org.quartz.SchedulerException;

import com.bee32.plover.scheduler.util.QuartzPlayer;

public class JobFastforwardTest
        extends QuartzPlayer {

    @Override
    protected void setup()
            throws SchedulerException {
    }

    public static void main(String[] args)
            throws SchedulerException, InterruptedException {
        new JobFastforwardTest().run();
    }

}
