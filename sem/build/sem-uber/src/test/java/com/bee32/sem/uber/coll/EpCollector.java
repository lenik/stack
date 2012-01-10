package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.orm.access.IEntityProcessor;
import com.bee32.plover.test.ServiceCollector;

public class EpCollector
        extends ServiceCollector<IEntityProcessor> {

    public static void main(String[] args)
            throws IOException {
        new EpCollector().collect();
    }

}
