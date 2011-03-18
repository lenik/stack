package com.bee32.plover.web.faces;

import javax.faces.webapp.FacesServlet;

import com.bee32.plover.servlet.test.ServletTestCase;
import com.bee32.plover.web.faces.CustomResourceResolver;

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

        // Faces Servlet
        stl.addServlet(FacesServlet.class, "*.jsf");
    }

}