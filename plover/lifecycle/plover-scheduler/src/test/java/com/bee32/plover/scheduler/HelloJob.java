package com.bee32.plover.scheduler;

import javax.inject.Inject;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.scheduler.util.Jobs;
import com.bee32.plover.scheduler.util.QuartzPlayer;
import com.bee32.plover.scheduler.util.Triggers;

@Component
@Lazy
public class HelloJob
        extends QuartzPlayer
        implements Job {

    static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Inject
    HelloBean helloBean;

    String name;
    String trig;

    static int seq = 0;
    static int strig = 0;

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
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello, " + name + "/" + trig + "/" + threadName + " -> " + helloBean);

        Scheduler sched = context.getScheduler();

        JobDetail jd = context.getJobDetail();
        jd.getJobDataMap().put("name", "J " + (++seq));
        sched.addJob(jd, true);

        TriggerKey key = new TriggerKey("timeout");

        SimpleTriggerImpl tr = Triggers.timeout("timeout", 10000);
        tr.getJobDataMap().put("trig", "T" + (++strig));

        // Thread.sleep(new Random().nextInt(1000));
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

    @Override
    protected void setup()
            throws SchedulerException {
        JobDetailImpl job = Jobs.declare("hello", HelloJob.class);

        JobDataMap map = new JobDataMap();
        map.put("name", "Ray");
        job.setJobDataMap(map);

        Trigger trigger = Triggers.timeout("timeout", 100);
        sched.scheduleJob(job, trigger);
    }

    public static void main(String[] args)
            throws Exception {
        new HelloJob().run();
    }

}
