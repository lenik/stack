package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.orm.entity.IEntityLifecycleAddon;
import com.bee32.plover.test.ServiceCollector;

public class ElaCollector
        extends ServiceCollector<IEntityLifecycleAddon> {

    public static void main(String[] args)
            throws IOException {
        new ElaCollector().collect();
    }

}
