package com.bee32.plover.arch.util;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.GlobalAppCtx;

public abstract class ApplicationContextPartialContext
        implements IPartialContext {

    public abstract ApplicationContext getAppCtx();

    public static GlobalApplicationContextPartialContext GLOBAL = new GlobalApplicationContextPartialContext();

}

class GlobalApplicationContextPartialContext
        extends ApplicationContextPartialContext {

    @Override
    public ApplicationContext getAppCtx() {
        return GlobalAppCtx.getApplicationContext();
    }

}
