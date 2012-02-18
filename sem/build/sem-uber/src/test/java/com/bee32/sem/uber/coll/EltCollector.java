package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.logging.IExceptionLogTargeter;
import com.bee32.plover.test.ServiceCollector;

public class EltCollector
        extends ServiceCollector<IExceptionLogTargeter> {

    public static void main(String[] args)
            throws IOException {
        new EltCollector().collect();
    }

}
