package com.bee32.plover.servlet.rabbits;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.bee32.plover.servlet.peripheral.DecoratedScl;

public class RootContextLoaderScl
        extends DecoratedScl {

    @Override
    public int getPriority() {
        return -10;
    }

    public RootContextLoaderScl() {
        super(ContextLoaderListener.class);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

}
