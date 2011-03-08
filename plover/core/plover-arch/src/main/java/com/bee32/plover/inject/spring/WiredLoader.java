package com.bee32.plover.inject.spring;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

public class WiredLoader {

    @Inject
    ApplicationContext applicationContext;

    public <T> T load(Class<T> type) {
        AutowireCapableBeanFactory acbf = applicationContext.getAutowireCapableBeanFactory();
        // return acbf.createBean(type);
        return acbf.getBean(type);
    }

}
