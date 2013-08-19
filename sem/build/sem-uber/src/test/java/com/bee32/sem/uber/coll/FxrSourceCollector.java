package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.world.monetary.AbstractFxrSource;
import com.bee32.sem.world.monetary.IFxrSource;

/**
 * 汇率源收集器
 *
 * <p lang="en">
 * FXR Source Collector
 *
 * @see AbstractFxrSource
 */
public class FxrSourceCollector
        extends ServiceCollector<IFxrSource> {

    public static void main(String[] args)
            throws IOException {
        new FxrSourceCollector().collect();
    }

}
