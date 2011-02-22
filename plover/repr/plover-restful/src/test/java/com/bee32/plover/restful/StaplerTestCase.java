package com.bee32.plover.restful;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.kohsuke.stapler.Stapler;

import com.bee32.plover.servlet.test.ServletTestCase;

public abstract class StaplerTestCase
        extends ServletTestCase {

    public void setup()
            throws Exception {
        logger.debug("Add stapler servlet");
        addServlet(Stapler.class, "/");
        addEventListener(new StaplerInitializer());
    }

    private Object _getRoot() {
        logger.debug("getRoot() is called");
        return getRoot();
    }

    protected abstract Object getRoot();

    class StaplerInitializer
            implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
            logger.debug("Context initialized");
            Stapler.setRoot(event, _getRoot());
        }

        @Override
        public void contextDestroyed(ServletContextEvent event) {
            logger.debug("Context destroyed");
            // Stapler.setRoot(event, null);
        }

    }

}
