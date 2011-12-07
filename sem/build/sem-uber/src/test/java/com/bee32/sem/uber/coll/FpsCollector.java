package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.IFeaturePlayerSupport;
import com.bee32.plover.test.ServiceCollector;

public class FpsCollector
        extends ServiceCollector<IFeaturePlayerSupport> {

    public static void main(String[] args)
            throws IOException {
        new FpsCollector().collect();
    }

}
