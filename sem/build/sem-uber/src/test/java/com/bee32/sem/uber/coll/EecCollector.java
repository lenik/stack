package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.logging.IExceptionEntryCompletion;
import com.bee32.plover.test.ServiceCollector;

public class EecCollector
        extends ServiceCollector<IExceptionEntryCompletion> {

    public static void main(String[] args)
            throws IOException {
        new EecCollector().collect();
    }

}
