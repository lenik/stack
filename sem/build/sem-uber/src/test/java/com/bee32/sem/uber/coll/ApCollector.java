package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.IAppProfile;
import com.bee32.plover.test.ServiceCollector;

public class ApCollector
        extends ServiceCollector<IAppProfile> {

    public static void main(String[] args)
            throws IOException {
        new ApCollector().collect();
    }

}
