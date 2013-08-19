package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.scheduler.quartz.AbstractQuartzJobConfigurer;
import com.bee32.plover.scheduler.quartz.IQuartzJobConfigurer;
import com.bee32.plover.test.ServiceCollector;

/**
 * Quartz作业收集器
 *
 * <p lang="en">
 * Quartz Job Collector
 *
 * @see AbstractQuartzJobConfigurer
 */
public class QjcCollector
        extends ServiceCollector<IQuartzJobConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new QjcCollector().collect();
    }

}
