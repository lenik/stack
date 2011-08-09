package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.test.AbstractWebAppConfigurer;
import com.bee32.plover.servlet.test.IWebAppConfigurer;
import com.bee32.plover.test.ServiceCollector;

/**
 * Please make {@link AbstractWebAppConfigurer} be DI-component at first.
 *
 * @see AbstractWebAppConfigurer
 */
public class WacCollector
        extends ServiceCollector<IWebAppConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new WacCollector().collect();
    }

}
