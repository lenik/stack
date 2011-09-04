package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.IApplicationLifecycle;
import com.bee32.plover.test.ServiceCollector;

public class AlcCollector
        extends ServiceCollector<IApplicationLifecycle> {

    public static void main(String[] args)
            throws IOException {
        new AlcCollector().collect();
    }

}
