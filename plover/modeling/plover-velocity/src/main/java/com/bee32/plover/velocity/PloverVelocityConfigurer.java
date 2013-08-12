package com.bee32.plover.velocity;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * 普拉瓦框架 Apache-Velocity 配置器
 */
@Component
@Lazy
public class PloverVelocityConfigurer
        extends VelocityConfigurer {

    @Inject
    PloverVelocityEngine velocityEngine;

    public PloverVelocityConfigurer() {
        setVelocityEngine(velocityEngine);

        setResourceLoaderPath("/WEB-INF/velocity");

        // Resource configLocation = null;
        // setConfigLocation(configLocation);
    }

}
