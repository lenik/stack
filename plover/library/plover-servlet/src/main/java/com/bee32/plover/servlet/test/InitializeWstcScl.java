package com.bee32.plover.servlet.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.servlet.peripheral.AbstractScl;
import com.bee32.plover.servlet.rabbits.RootContextLoaderScl;

/**
 * Initialize WSTC.appctx after the context started.
 *
 * (So the main-app & webapp share the same appctx)
 *
 * You should make sure {@link ContextLoaderListener} happen before this SCL.
 *
 * @see RootContextLoaderScl
 */
public class InitializeWstcScl
        extends AbstractScl {

    WiredServletTestCase wstc;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        WiredServletTestCase wstc = ServletTestCase.getInstanceFromContext(sc, WiredServletTestCase.class);
        if (wstc != null) {
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
            wstc.applicationInitialized(applicationContext);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        WiredServletTestCase wstc = ServletTestCase.getInstanceFromContext(sc, WiredServletTestCase.class);
        if (wstc != null) {
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
            wstc.applicationDestroyed(applicationContext);
        }
    }

}
