package com.bee32.sem.world.monetary;

import java.io.IOException;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.scheduler.util.Triggers;
import com.bee32.sem.world.monetary.impl.DiscreteFxrProvider;

/**
 * 汇率提交作业
 *
 * <p lang="en">
 * FXR Commit Job
 */
public class FxrCommitJob
        extends DataService
        implements Job {

    public static final String SOURCE_KEY = "source";
    public static final String INTERVAL_KEY = "interval";

    static Logger logger = LoggerFactory.getLogger(FxrCommitJob.class);

    @Inject
    IFxrProvider fxrProvider = new DiscreteFxrProvider();

    IFxrSource source;
    int intervalMs = -1;

    boolean locked;

    @Transactional(readOnly = false)
    @Override
    public synchronized void execute(JobExecutionContext context)
            throws JobExecutionException {
        if (locked)
            throw new IllegalStateException("Non-reentrant.");
        locked = true;

        try {
            _execute(context);
        } catch (JobExecutionException e) {
            throw e;
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        } finally {
            locked = false;
        }
    }

    void _execute(JobExecutionContext context)
            throws SchedulerException, IOException {

        if (intervalMs == -1)
            throw new IllegalUsageException("Interval isn't set.");

        Scheduler sched = context.getScheduler();

        FxrTable table = downloadAndCommit();

        System.err.println("Download: " + source.getClass().getSimpleName() + " -> " + table);

        if (table == null) {
            if (source.isFinite()) {
                // completed.
                logger.info("FXR Source is completed: " + source);
                JobKey jobKey = context.getJobDetail().getKey();
                sched.deleteJob(jobKey);
                return;
            }
        }

        TriggerKey triggerKey = context.getTrigger().getKey();

        SimpleTriggerImpl trigger = Triggers.timeout(triggerKey.getName(), intervalMs);

        // Reinit interval
        JobDataMap triggerDataMap = new JobDataMap();
        triggerDataMap.put(INTERVAL_KEY, getInterval());
        trigger.setJobDataMap(triggerDataMap);

        // sched.rescheduleJob(triggerKey, trigger);
    }

    protected FxrTable downloadAndCommit()
            throws IOException {
        FxrTable table = source.download();
        if (table == null)
            return null;

        logger.debug("Fxr-Commit: " + table.toString());
        fxrProvider.commit(table);

        return table;
    }

    public IFxrSource getSource() {
        return source;
    }

    public void setSource(IFxrSource source) {
        this.source = source;
    }

    public int getInterval() {
        return intervalMs / 1000;
    }

    public void setInterval(int interval) {
        this.intervalMs = interval * 1000;
    }

}
