package com.bee32.plover.scheduler.fp;

import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.scheduler.util.Jobs;
import com.bee32.plover.scheduler.util.QuartzPlayer;
import com.bee32.plover.scheduler.util.Triggers;

@DisallowConcurrentExecution
public class JobReentrantTest
        extends QuartzPlayer
        implements Job {

    static Logger logger = LoggerFactory.getLogger(JobReentrantTest.class);

    static int nextInstance = 0;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        int instance = ++nextInstance;
        logger.info("Job Start " + instance);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        logger.info("Job End " + instance);
    }

    @Override
    protected void setup()
            throws SchedulerException {
        JobDetail job1 = Jobs.declare(JobReentrantTest.class);
        SimpleTriggerImpl trigger = Triggers.timeout("timeout", 0, 100, 10); // immediately
        trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        sched.scheduleJob(job1, trigger);
    }

    public static void main(String[] args)
            throws SchedulerException, InterruptedException {
        new JobReentrantTest().run();
    }

}
