package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletContextEvent;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractScl
        implements IServletContextListener {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
