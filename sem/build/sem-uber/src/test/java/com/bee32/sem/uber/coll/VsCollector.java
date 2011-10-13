package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.model.validation.IValidationSwitcher;
import com.bee32.plover.test.ServiceCollector;

public class VsCollector
        extends ServiceCollector<IValidationSwitcher> {

    public static void main(String[] args)
            throws IOException {
        new VsCollector().collect();
    }

}
