package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.exception.IExceptionHandler;
import com.bee32.plover.test.ServiceCollector;

public class ExceptionHandlerCollector
        extends ServiceCollector<IExceptionHandler> {

    public static void main(String[] args)
            throws IOException {
        new ExceptionHandlerCollector().collect();
    }

}
