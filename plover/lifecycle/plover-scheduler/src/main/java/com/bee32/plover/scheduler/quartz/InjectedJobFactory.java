package com.bee32.plover.scheduler.quartz;

import java.util.Map;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.simpl.PropertySettingJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @see org.springframework.scheduling.quartz.SpringBeanJobFactory
 */
public class InjectedJobFactory
        extends PropertySettingJobFactory {

    static Logger logger = LoggerFactory.getLogger(InjectedJobFactory.class);

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler)
            throws SchedulerException {

        SchedulerContext schedulerContext = scheduler.getContext();

        JobDetail jobDetail = bundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        Job job;

        try {
            // logger.debug("Producing instance of Job '" + jobDetail.getKey()
            // + "', class=" + jobClass.getName());

            job = instantiate(jobClass, schedulerContext);

        } catch (Exception e) {
            SchedulerException se = new SchedulerException("Problem instantiating class '"
                    + jobDetail.getJobClass().getName() + "'", e);
            throw se;
        }

        JobDataMap jobDataMap = new JobDataMap();
        mergeProperties(jobDataMap, scheduler.getContext());
        mergeProperties(jobDataMap, bundle.getJobDetail().getJobDataMap());
        mergeProperties(jobDataMap, bundle.getTrigger().getJobDataMap());

        setBeanProps(job, jobDataMap);

        return job;
    }

    protected <T> T instantiate(Class<T> beanType, SchedulerContext schedulerContext)
            throws ReflectiveOperationException {
        T bean;

        // bean = clazz.newInstance();
        ApplicationContext appctx = QuartzConfig.getApplicationContext(schedulerContext);
        bean = appctx.getBean(beanType);

        return bean;
    }

    protected void mergeProperties(JobDataMap jobDataMap, Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            Object _key = entry.getKey();
            if (_key == null)
                continue;
            String key = _key.toString();

            if (key.startsWith("."))
                continue;

            jobDataMap.put(key, entry.getValue());
        }
    }

}