package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.orm.config.IDatabaseConfigurer;
import com.bee32.plover.test.ServiceCollector;

public class DcCollector
        extends ServiceCollector<IDatabaseConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new DcCollector().collect();
    }

}
