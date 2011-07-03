package com.bee32.plover.servlet.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.spring.ContextConfigurationUtil;
import com.bee32.plover.servlet.context.ServletContextUtil;
import com.bee32.plover.servlet.mvc.MVCConfig;
import com.bee32.plover.servlet.rabbits.RabbitServletContext;
import com.bee32.plover.servlet.rabbits.RootContextLoaderListener;
import com.bee32.plover.test.WiredTestCase;

@Import(WiredTestCase.class)
public abstract class WiredServletTestCase
        extends ServletTestCase {

    private ApplicationContext applicationContext;

    protected WiredServletTestCase() {
    }

    /**
     * Happened before applicationContext is initialized.
     */
    @Override
    protected void configureContext() {
        super.configureContext();

        if (applicationContext == null) {

            // Initialize after the context started.
            stl.addEventListener(new RootContextListener());

            RabbitServletContext context = stl.getServletContext();
            String locations = ContextConfigurationUtil.getConcatConfigLocations(getClass());
            context.addInitParam("contextConfigLocation", locations);

        } else {

            // Initialize immediately
            String rootAppCtx = ServletContextUtil.rootApplicationContextKey;
            stl.setAttribute(rootAppCtx, applicationContext);

            if (applicationContext instanceof WebApplicationContext) {
                String webRootAppCtx = ServletContextUtil.webRootApplicationContextKey;
                stl.setAttribute(webRootAppCtx, applicationContext);
            }

        }
    }

    protected boolean isSpringMVCEnabled() {
        return true;
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        if (isSpringMVCEnabled()) {
            // Add spring mvc support here.
            stl.addServlet("spring-dispatcher", DispatcherServlet.class, "*" + MVCConfig.SUFFIX);
        }
    }

    class RootContextListener
            extends RootContextLoaderListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
            super.contextInitialized(event);

            ServletContext sc = event.getServletContext();
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
            applicationInitialized(applicationContext);
        }

        @Override
        public void contextDestroyed(ServletContextEvent event) {
            ServletContext sc = event.getServletContext();
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
            applicationDestroyed(applicationContext);

            super.contextDestroyed(event);
        }

    }

    protected void applicationInitialized(ApplicationContext applicationContext) {
    }

    protected void applicationDestroyed(ApplicationContext applicationContext) {
    }

}
