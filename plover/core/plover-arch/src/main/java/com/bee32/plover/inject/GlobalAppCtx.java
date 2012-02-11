package com.bee32.plover.inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Global application context holder.
 *
 * This service should be eargely activated by Spring CDI.
 */
@Component
public class GlobalAppCtx
        implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        appctx = applicationContext;
    }

    public static ApplicationContext appctx;

    public static ApplicationContext getApplicationContext() {
        if (appctx == null)
            throw new IllegalStateException("Application context isn't initialized, yet.");
        return appctx;
    }

}
