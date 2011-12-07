package com.bee32.plover.inject;

import org.springframework.context.ApplicationContext;

/**
 * Global application context holder.
 *
 * This service should be eargely activated by Spring CDI.
 *
 * @see StaticServiceActivator
 */
public class GlobalAppCtx
        extends AbstractActivatorService {

    static ApplicationContext _appctx;

    @Override
    public void activate() {
        _appctx = appctx;
    }

    public static ApplicationContext getApplicationContext() {
        return _appctx;
    }

}
