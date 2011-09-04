package com.bee32.plover.servlet.test;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;
import com.bee32.plover.servlet.context.ServletContextUtil;
import com.bee32.plover.servlet.mvc.MVCConfig;
import com.bee32.plover.servlet.rabbits.RabbitServletContext;

public class SpringWac
        extends OuterWac<WiredServletTestCase> {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    protected void configureContext(ServletTestLibrary stl, WiredServletTestCase outer) {
        ApplicationContext appctx = outer.applicationContext;

        if (appctx == null) {

            /** @see WiredServletTestCaseAppctxScl */

            Class<?> outerType = outer.getClass();
            String locations = ContextConfigurationUtil.getConcatConfigLocations(outerType);

            RabbitServletContext context = stl.getServletContext();
            context.addInitParam("contextConfigLocation", locations);

        } else {

            // Initialize immediately
            String rootAppCtx = ServletContextUtil.rootApplicationContextKey;
            stl.setAttribute(rootAppCtx, appctx);

            if (appctx instanceof WebApplicationContext) {
                String webRootAppCtx = ServletContextUtil.webRootApplicationContextKey;
                stl.setAttribute(webRootAppCtx, appctx);
            }

        }
    }

    @Override
    protected void configureServlets(ServletTestLibrary stl, WiredServletTestCase outer) {
        if (outer.isSpringMVCEnabled()) {
            // Add spring mvc support here.
            stl.addServlet("spring-dispatcher", DispatcherServlet.class, "*" + MVCConfig.SUFFIX);
        }
    }

}
