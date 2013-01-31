package com.bee32.ape.html.apex.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApexConfig
        implements InitializingBean {

    static final Logger logger = LoggerFactory.getLogger(ApexConfig.class);

    @Value("${activiti.ui.environment}")
    String environment;

    public String getEnvironment() {
        return environment;
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        logger.debug("Activiti environment was set to: " + environment);
    }

}
