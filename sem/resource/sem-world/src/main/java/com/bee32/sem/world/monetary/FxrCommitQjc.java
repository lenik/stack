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
 * 汇率提交作业 Quartz 自动化
 *
 * 本 Quartz 作业（配置项）用于触发 {@link FxrCommitJob}。
 *
 * FXR Updater Job:
 *
 * For each fxr source: fxr
 */
public class FxrCommitQjc
        extends AbstractQuartzJobConfigurer {

    static Logger logger = LoggerFactory.getLogger(FxrCommitQjc.class);

    // (ms) delay 100 seconds to avoid spin locks at bootstrap.
    static final int INITIAL_DELAY = 10000000;

    @Override
    public void load(SchedulerFactory schedulerFactory)
            throws SchedulerException {

        Scheduler sched = schedulerFactory.getScheduler();

        // Declare a commit job for each fxr-source.
        for (IFxrSource fxrSource : ServiceLoader.load(IFxrSource.class)) {
            String jobName = "FXR:" + fxrSource.getClass().getName();

            JobDetailImpl jobDetail = Jobs.declare(jobName, FxrCommitJob.class);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(FxrCommitJob.SOURCE_KEY, fxrSource);
            jobDetail.setJobDataMap(jobDataMap);

            SimpleTriggerImpl trigger = Triggers.timeout("T:" + jobName, INITIAL_DELAY);

            JobDataMap triggerDataMap = new JobDataMap();

            int interval = fxrSource.getPreferredInterval();
            triggerDataMap.put(FxrCommitJob.INTERVAL_KEY, interval);
            trigger.setJobDataMap(triggerDataMap);

            sched.scheduleJob(jobDetail, trigger);
        }

    }

}
