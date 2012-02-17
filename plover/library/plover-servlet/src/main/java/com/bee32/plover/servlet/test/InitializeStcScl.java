package com.bee32.plover.servlet.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.peripheral.AbstractScl;
import com.bee32.plover.servlet.rabbits.RabbitServer;

public class InitializeStcScl
        extends AbstractScl {

    static Logger logger = LoggerFactory.getLogger(InitializeStcScl.class);

    /**
     * How to know myself?
     *
     * @see RabbitServer#getInstanceFromContext(ServletContext)
     * @see C_Wac
     */

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Test servlet context is initialized.");
        ServletContext servletContext = sce.getServletContext();
        ServletTestCase application = ServletTestCase.getInstanceFromContext(servletContext);
        application.servletContext = servletContext;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Test servlet context is destroyed.");
        ServletContext servletContext = sce.getServletContext();
        ServletTestCase application = ServletTestCase.getInstanceFromContext(servletContext);
        application.servletContext = null;
    }

}
