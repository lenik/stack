package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.peripheral.IHttpSessionListener;
import com.bee32.plover.test.ServiceCollector;

public class HslCollector
        extends ServiceCollector<IHttpSessionListener> {

    public static void main(String[] args)
            throws IOException {
        new HslCollector().collect();
    }

}
