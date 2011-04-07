package com.bee32.plover.inject.cref;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.spring.ApplicationContextBuilder;

public class ConfigSupport {

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            synchronized (this) {
                if (applicationContext == null) {
                    applicationContext = buildApplicationContext();
                }
            }
        }
        return applicationContext;
    }

    public ApplicationContext buildApplicationContext() {
        return ApplicationContextBuilder.buildSelfHostedContext(getClass());
    }

}
