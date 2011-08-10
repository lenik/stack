package com.bee32.plover.scheduler.util;

import java.util.Map;

import javax.free.Strings;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.impl.JobDetailImpl;

public class Jobs {

    public static JobDetailImpl declare(Class<? extends Job> jobClass) {
        String typeName = jobClass.getSimpleName();
        String defaultName = Strings.lcfirst(typeName);
        return declare(defaultName, jobClass);
    }

    public static JobDetailImpl declare(String name, Class<? extends Job> jobClass) {
        return declare(name, jobClass, null);
    }

    public static JobDetailImpl declare(String name, Class<? extends Job> jobClass, Map<?, ?> dataMap) {
        JobDetailImpl job = new JobDetailImpl();
        job.setName(name);
        // job.setGroup(DEFAULT_GROUP);
        job.setJobClass(jobClass);

        if (dataMap != null) {
            JobDataMap jdm;
            if (dataMap instanceof JobDataMap)
                jdm = (JobDataMap) dataMap;
            else
                jdm = new JobDataMap(dataMap);
            job.setJobDataMap(jdm);
        }

        return job;
    }

}
