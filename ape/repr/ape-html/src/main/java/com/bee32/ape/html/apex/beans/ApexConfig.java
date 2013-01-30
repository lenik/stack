package com.bee32.ape.html.apex.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApexConfig {

    @Value("${activiti.ui.environment}")
    String environment;

    public String getEnvironment() {
        return environment;
    }

}
