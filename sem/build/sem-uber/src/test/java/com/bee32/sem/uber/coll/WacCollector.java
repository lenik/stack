package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.IWebAppConfigurer;
import com.bee32.plover.test.ServiceCollector;

/**
 * Web-App 碎片收集器
 *
 * <p lang="en">
 * Web-App fragment Collector
 *
 * Please make {@link AbstractWac} be DI-component at first.
 *
 * @see AbstractWac
 */
public class WacCollector
        extends ServiceCollector<IWebAppConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new WacCollector().collect();
    }

}
