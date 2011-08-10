package com.bee32.sem.world.monetary;

import java.util.ServiceLoader;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.scheduler.quartz.AbstractQuartzJobConfigurer;
import com.bee32.plover.scheduler.util.Jobs;
import com.bee32.plover.scheduler.util.Triggers;

/**
 * FXR Updater Job:
 *
 * For each fxr source: fxr
 */
public class FxrCommitQjc
        extends AbstractQuartzJobConfigurer {

    static Logger logger = LoggerFactory.getLogger(FxrCommitQjc.class);

    static final int INITIAL_DELAY = 0; // Immediately.

    @Override
    public void load(SchedulerFactory schedulerFactory)
            throws SchedulerException {

        Scheduler sched = schedulerFactory.getScheduler();

        for (IFxrSource fxrSource : ServiceLoader.load(IFxrSource.class)) {

            String jobName = "FXR:" + fxrSource.getClass().getName();

            JobDetailImpl jobDetail = Jobs.declare(jobName, FxrCommitJob.class);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("source", fxrSource);
            jobDetail.setJobDataMap(jobDataMap);

            SimpleTriggerImpl trigger = Triggers.timeout("T:" + jobName, INITIAL_DELAY);

            JobDataMap triggerDataMap = new JobDataMap();

            int interval = fxrSource.getPreferredInterval();
            triggerDataMap.put("interval", interval);

            sched.scheduleJob(jobDetail, trigger);
        }

    }

}
