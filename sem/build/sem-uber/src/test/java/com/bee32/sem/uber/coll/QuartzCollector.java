package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.scheduler.quartz.IQuartzConfigurer;
import com.bee32.plover.test.ServiceCollector;

public class QuartzCollector
        extends ServiceCollector<IQuartzConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new QuartzCollector().collect();
    }

}
