package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.peripheral.IServletContextListener;
import com.bee32.plover.test.ServiceCollector;

public class SclCollector
        extends ServiceCollector<IServletContextListener> {

    public static void main(String[] args)
            throws IOException {
        new SclCollector().collect();
    }

}
