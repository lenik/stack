package com.bee32.plover.servlet.util;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.ApplicationContextPartialContext;
import com.bee32.plover.inject.GlobalAppCtx;

public class BootstrapApplicationContextPartialContext
        extends ApplicationContextPartialContext {

    /**
     * @return maybe null.
     */
    @Override
    public ApplicationContext getAppCtx() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();
        if (context == null)
            context = GlobalAppCtx.getApplicationContext();
        return context;
    }

    public static final BootstrapApplicationContextPartialContext INSTANCE = new BootstrapApplicationContextPartialContext();

}
