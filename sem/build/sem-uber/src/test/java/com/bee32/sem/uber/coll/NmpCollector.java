package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.util.res.INlsMessageProcessor;
import com.bee32.plover.test.ServiceCollector;

public class NmpCollector
        extends ServiceCollector<INlsMessageProcessor> {

    public static void main(String[] args)
            throws IOException {
        new NmpCollector().collect();
    }

}
