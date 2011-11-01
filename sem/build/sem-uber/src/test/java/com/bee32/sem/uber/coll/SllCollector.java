package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.site.ISiteLifecycleListener;
import com.bee32.plover.test.ServiceCollector;

public class SllCollector
        extends ServiceCollector<ISiteLifecycleListener> {

    public static void main(String[] args)
            throws IOException {
        new SllCollector().collect();
    }

}
