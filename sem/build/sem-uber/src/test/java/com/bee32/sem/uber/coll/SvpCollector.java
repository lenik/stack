package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.api.ISalaryVariableProvider;

public class SvpCollector
        extends ServiceCollector<ISalaryVariableProvider> {

    public static void main(String[] args)
            throws IOException {
        new SvpCollector().collect();
    }

}
