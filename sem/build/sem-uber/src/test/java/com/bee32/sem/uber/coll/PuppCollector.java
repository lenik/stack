package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.orm.unit.IPersistenceUnitPostProcessor;
import com.bee32.plover.test.ServiceCollector;

public class PuppCollector
        extends ServiceCollector<IPersistenceUnitPostProcessor> {

    public static void main(String[] args)
            throws IOException {
        new PuppCollector().collect();
    }

}
