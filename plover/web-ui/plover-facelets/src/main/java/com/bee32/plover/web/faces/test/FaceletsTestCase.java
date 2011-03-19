package com.bee32.plover.web.faces.test;

import javax.faces.webapp.FacesServlet;

import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.test.ServletTestCase;

enum FaceletsProvider {

    JSF_RI, //

    APACHE_MYFACES, //

}

public class FaceletsTestCase
        extends ServletTestCase {

    static final FaceletsProvider faceletsProvider = FaceletsProvider.APACHE_MYFACES;

    static Logger logger = LoggerFactory.getLogger(FaceletsTestCase.class);

    @Override
    protected void configureServlets() {
        // stl.setAttribute("facelets.RESOURCE_RESOLVER", CustomResourceResolver.class.getName());

        // - Use Documents Saved as *.xhtml
        stl.setAttribute("javax.faces.DEFAULT_SUFFIX", ".xhtml");

        // Special Debug Output for Development
        stl.setAttribute("facelets.DEVELOPMENT", "true");

        // Optional JSF-RI Parameters to Help Debug
        stl.setAttribute("com.sun.faces.validateXml", "true");
        stl.setAttribute("com.sun.faces.verifyObjects", "true");

        switch (faceletsProvider) {
        case JSF_RI:
            // For jsf-ri only:
            // stl.addEventListener(new ConfigureListener());
            // stl.addEventListener(new FaceletsWebappLifecycleListener());
            throw new UnsupportedOperationException("JSF-RI isn't supported anymore");

        case APACHE_MYFACES:
            stl.addEventListener(MyfacesReinitDummyServlet.myfacesSCL);
            // stl.addServlet(MyfacesReinitDummyServlet.class, "/__mrds__").setInitOrder(1000);
            break;
        }

        // Faces Servlet, must be load-on-startup, but STL seems not support.
        ServletHolder facesServlet = stl.addServlet(FacesServlet.class, "*.jsf");
        facesServlet.setInitOrder(0);
    }
}