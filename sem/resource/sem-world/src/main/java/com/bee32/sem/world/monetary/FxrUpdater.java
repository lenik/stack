package com.bee32.sem.world.monetary;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FxrUpdater
        implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        context.getJobRunTime();
    }

}
