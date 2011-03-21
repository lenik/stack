package com.bee32.plover.web.faces.test;

import javax.faces.webapp.FacesServlet;
import javax.free.IllegalUsageException;

import org.apache.myfaces.webapp.StartupServletContextListener;
import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.test.OverlappedBases;
import com.bee32.plover.servlet.test.RabbitServletContext;
import com.bee32.plover.servlet.test.WiredServletTestCase;
import com.bee32.plover.web.faces.ClassResourceResolver;
import com.bee32.plover.web.faces.FacesConstants;

enum FaceletsProvider {

    JSF_RI, //

    APACHE_MYFACES, //

}

public class FaceletsTestCase
        extends WiredServletTestCase
        implements FacesConstants {

    static final FaceletsProvider faceletsProvider = FaceletsProvider.APACHE_MYFACES;

    static Logger logger = LoggerFactory.getLogger(FaceletsTestCase.class);

    private boolean checkAdditionalServlets;

    public FaceletsTestCase() {
        super();
        init();
    }

    public FaceletsTestCase(Class<?> altBaseClass) {
        super(altBaseClass);
        init();
    }

    private void init() {
        OverlappedBases.add("resources/");
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        RabbitServletContext context = stl.getServletContext();
        {
            context.addInitParam(RESOURCE_RESOLVER, ClassResourceResolver.class.getName());

            // - Use Documents Saved as *.xhtml
            context.addInitParam(DEFAULT_SUFFIX, ".xhtml");

            // Special Debug Output for Development
            context.addInitParam(DEVELOPMENT, "true");

            // Optional JSF-RI Parameters to Help Debug
            // sm.addInitParam(VALIDATE_XML, "true");
            // sm.addInitParam(VERIFY_OBJECTS, "true");
        }

        switch (faceletsProvider) {
        case JSF_RI:
            // For jsf-ri only:
            // stl.addEventListener(new ConfigureListener());
            // stl.addEventListener(new FaceletsWebappLifecycleListener());
            throw new UnsupportedOperationException("JSF-RI isn't supported anymore");

        case APACHE_MYFACES:
            stl.addEventListener(new StartupServletContextListener());
            break;
        }

        // Faces Servlet, must be load-on-startup, but STL seems not support.
        ServletHolder facesServlet = stl.addServlet(FacesServlet.class, "*.jsf");
        facesServlet.setInitOrder(0);
    }

    @Override
    protected final void configureServlets() {
        configureAdditionalServlets();

        if (!checkAdditionalServlets)
            throw new IllegalUsageException("configureAdditionalServlets is overrided.");
    }

    protected void configureAdditionalServlets() {
        checkAdditionalServlets = true;
    }

}