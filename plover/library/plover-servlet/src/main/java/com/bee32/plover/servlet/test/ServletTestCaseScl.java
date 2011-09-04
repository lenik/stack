package com.bee32.plover.servlet.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.peripheral.AbstractScl;
import com.bee32.plover.servlet.rabbits.RabbitServer;

public class ServletTestCaseScl
        extends AbstractScl {

    static Logger logger = LoggerFactory.getLogger(ServletTestCaseScl.class);

    /**
     * How to know myself?
     *
     * @see RabbitServer#getInstanceFromContext(ServletContext)
     * @see OuterWac
     */
    ServletTestCase stc;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Test servlet context is initialized.");
        ServletContext servletContext = sce.getServletContext();
        ServletTestCase stc = ServletTestCase.getInstanceFromContext(servletContext);
        stc.servletContext = servletContext;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Test servlet context is destroyed.");
        ServletContext servletContext = sce.getServletContext();
        ServletTestCase stc = ServletTestCase.getInstanceFromContext(servletContext);
        stc.servletContext = null;
    }

}
