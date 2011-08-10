package com.bee32.plover.scheduler;

import java.io.IOException;

import com.bee32.plover.scheduler.quartz.IQuartzConfigurer;
import com.bee32.plover.test.ServiceCollector;

public class HelloQuartzCollector
        extends ServiceCollector<IQuartzConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new HelloQuartzCollector().collect();
    }

}
