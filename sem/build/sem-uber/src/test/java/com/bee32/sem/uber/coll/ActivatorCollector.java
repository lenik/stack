package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.inject.AbstractActivatorService;
import com.bee32.plover.inject.IActivatorService;
import com.bee32.plover.test.ServiceCollector;

/**
 * @see AbstractActivatorService
 */
public class ActivatorCollector
        extends ServiceCollector<IActivatorService> {

    public static void main(String[] args)
            throws IOException {
        new ActivatorCollector().collect();
    }

}
