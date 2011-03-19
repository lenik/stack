package com.bee32.plover.web.faces.test;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bee32.plover.servlet.test.ServletTestCase;
import com.bee32.plover.web.faces.CustomResourceResolver;
import com.sun.faces.application.WebappLifecycleListener;
import com.sun.faces.config.ConfigureListener;

public class FaceletsTestCase
        extends ServletTestCase {

    @Override
    protected void configureServlets() {
        stl.setAttribute("facelets.RESOURCE_RESOLVER", //
                CustomResourceResolver.class.getName());

        // - Use Documents Saved as *.xhtml
        stl.setAttribute("javax.faces.DEFAULT_SUFFIX", ".xhtml");

        // Special Debug Output for Development
        stl.setAttribute("facelets.DEVELOPMENT", "true");

        // Optional JSF-RI Parameters to Help Debug
        stl.setAttribute("com.sun.faces.validateXml", "true");
        stl.setAttribute("com.sun.faces.verifyObjects", "true");

        stl.addEventListener(new ConfigureListener());

        final WebappLifecycleListener lifecycle = new WebappLifecycleListener();

        stl.addEventListener(new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                lifecycle.contextInitialized(sce);
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                lifecycle.contextDestroyed(sce);
            }

        });

        // Faces Servlet
        stl.addServlet(FacesServlet.class, "*.jsf");
    }

}