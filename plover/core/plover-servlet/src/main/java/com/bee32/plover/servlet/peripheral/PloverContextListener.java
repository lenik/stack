package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PloverContextListener
        implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // sce.getServletContext().getContextPath();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

}
