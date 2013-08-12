package com.bee32.plover.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * 普拉瓦 Apache-Velocity 引擎定制
 *
 * @see VelocityConfig
 * @see VelocityConfigurer
 */
@Component
@Lazy
public class PloverVelocityEngine
        extends VelocityEngine
        implements VelocityConstants {

    public PloverVelocityEngine() {
        synchronized (PloverVelocityEngine.class) {

            setProperty("file" + _RESOURCE_LOADER_CLASS, //
                    ClasspathResourceLoader.class.getName());

            setProperty(RUNTIME_REFERENCES_STRICT, "true");

            if (firstInstance == null)
                firstInstance = this;
        }
    }

    private static PloverVelocityEngine firstInstance;

    public static synchronized PloverVelocityEngine getInstance() {
        if (firstInstance == null) {
            synchronized (PloverVelocityEngine.class) {
                if (firstInstance == null) {
                    firstInstance = new PloverVelocityEngine();
                }
            }
        }
        return firstInstance;
    }

}
