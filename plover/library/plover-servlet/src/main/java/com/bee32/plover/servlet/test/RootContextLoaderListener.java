package com.bee32.plover.servlet.test;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class RootContextLoaderListener
        extends ContextLoaderListener {

    public RootContextLoaderListener() {
        super();
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
