package com.bee32.ape.html.apex.beans;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

@Component
public class ApexConfig
        implements InitializingBean {

    static final String ACTIVITI_UI_ENVIRONMENT = "activiti.ui.environment";

    String environment;

    // TODO
    @Inject
    PropertiesPropertySource pps;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        environment = (String) pps.getProperty(ACTIVITI_UI_ENVIRONMENT);
    }

    public String getEnvironment() {
        return environment;
    }

}
