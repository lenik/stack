package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.test.ServiceCollector;

@NotAComponent
public class ScCollector
        extends ServiceCollector<ServiceCollector<?>> {

    public static void main(String[] args)
            throws IOException {
        new ScCollector().collect();
    }

}
