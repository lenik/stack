package com.bee32.plover.scheduler;

import java.util.Date;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.simpl.PropertySettingJobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob
        implements Job {

    static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    String name;
    String trig;

    static int seq = 0;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            _execute(context);
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage(), e);
        }
    }

    void _execute(JobExecutionContext context)
            throws Exception {
        System.out.println("Hello, " + name + " (trig=" + trig + ")");

        Scheduler sched = context.getScheduler();

        JobDetail jd = context.getJobDetail();
        jd.getJobDataMap().put("name", "T " + (++seq));
        sched.addJob(jd, true);

        TriggerKey key = new TriggerKey("timeout");
        SimpleTriggerImpl tr = timeout(10);
        Thread.sleep(new Random().nextInt(1000));
        sched.rescheduleJob(key, tr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrig() {
        return trig;
    }

    public void setTrig(String trig) {
        this.trig = trig;
    }

    static void start()
            throws Exception {
        Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
        sched.setJobFactory(new PropertySettingJobFactory());

        sched.start();
        JobDetailImpl job = new JobDetailImpl();
        job.setName("hello");
        job.setJobClass(HelloJob.class);

        JobDataMap map = new JobDataMap();
        map.put("name", "Ray");
        job.setJobDataMap(map);

        Trigger trigger = timeout(100);
        sched.scheduleJob(job, trigger);

        Thread.sleep(1000000);

        sched.shutdown(true); // wait=false

    }

    public static void main(String[] args)
            throws Exception {
        start();
    }

    static SimpleTriggerImpl timeout(int ms) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();

        trigger.setName("timeout");

        long t = new Date().getTime();

        trigger.setStartTime(new Date(t + ms));
        trigger.setRepeatInterval(5000);
        trigger.setRepeatCount(3);

        JobDataMap tmap = new JobDataMap();
        tmap.put("trig", "abc");
        trigger.setJobDataMap(tmap);

        return trigger;
    }

}
