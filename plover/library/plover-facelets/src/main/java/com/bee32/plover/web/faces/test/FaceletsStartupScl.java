package com.bee32.plover.web.faces.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.myfaces.webapp.StartupServletContextListener;

import com.bee32.plover.servlet.peripheral.AbstractScl;

public class FaceletsStartupScl
        extends AbstractScl {

    ServletContextListener myfacesScl = new StartupServletContextListener();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        switch (FaceletsTestCase.faceletsProvider) {
        case JSF_RI:
            // For jsf-ri only:
            // stl.addEventListener(new ConfigureListener());
            // stl.addEventListener(new FaceletsWebappLifecycleListener());
            throw new UnsupportedOperationException("JSF-RI isn't supported anymore");

        case APACHE_MYFACES:
            /**
             * Normally, servlet containers will automatically load and process .tld files at
             * startup time, and therefore register and run this class automatically.
             */
            // myfacesScl.contextInitialized(sce);
            break;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        switch (FaceletsTestCase.faceletsProvider) {
        case APACHE_MYFACES:
            // myfacesScl.contextDestroyed(sce);
        }
    }

}
