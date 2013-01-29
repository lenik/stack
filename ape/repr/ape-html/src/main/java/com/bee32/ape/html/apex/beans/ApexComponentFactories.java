package com.bee32.ape.html.apex.beans;

import javax.inject.Inject;

import org.activiti.explorer.ComponentFactories;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
// @PerSite
public class ApexComponentFactories
        extends ComponentFactories
        implements InitializingBean {

    @Inject
    ApexConfig config;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setEnvironment(config.environment);
    }

}
