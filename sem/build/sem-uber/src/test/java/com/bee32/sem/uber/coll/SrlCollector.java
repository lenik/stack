package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.peripheral.IServletRequestListener;
import com.bee32.plover.test.ServiceCollector;

public class SrlCollector
        extends ServiceCollector<IServletRequestListener> {

    public static void main(String[] args)
            throws IOException {
        new SrlCollector().collect();
    }

}
