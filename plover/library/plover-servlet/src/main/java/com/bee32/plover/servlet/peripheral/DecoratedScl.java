package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public abstract class DecoratedScl
        extends AbstractScl {

    ServletContextListener impl;

    public DecoratedScl(Class<? extends ServletContextListener> sclClass) {
        if (sclClass == null)
            throw new NullPointerException("sclClass");
        try {
            ServletContextListener scl = sclClass.newInstance();
            this.impl = scl;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public DecoratedScl(ServletContextListener impl) {
        if (impl == null)
            throw new NullPointerException("impl");
        this.impl = impl;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        impl.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        impl.contextDestroyed(sce);
    }

}
