package com.bee32.plover.web.faces.test;

import java.util.ArrayList;
import java.util.List;

import javax.faces.webapp.FacesServlet;
import javax.free.IllegalUsageException;
import javax.free.StringArray;

import org.apache.myfaces.webapp.StartupServletContextListener;
import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

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

    private List<String> taglibs = new ArrayList<String>();

    public FaceletsTestCase() {
        OverlappedBases.add("resources/");
    }

    /**
     * Please call this method in constructore, or configContext.
     *
     * TODO META-INF taglib.xml isn't read by myfaces-impl?
     *
     * It's recommendded to export taglib by declare taglib.xml files under META-INF/.
     */
    protected final void addTagLibrary(String taglib) {
        taglibs.add(taglib);
    }

    protected boolean isDebugMode() {
        return false;
    }

    protected boolean isStrictMode() {
        return false;
    }

    protected int getRefreshPeriod() {
        return 300;
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        RabbitServletContext context = stl.getServletContext();
        {

            if (!taglibs.isEmpty()) {
                String libraries = StringArray.join(";", taglibs);
                context.addInitParam(LIBRARIES, libraries);
            }

            context.addInitParam(RESOURCE_RESOLVER, ClassResourceResolver.class.getName());

            // - Use Documents Saved as *.xhtml
            context.addInitParam(DEFAULT_SUFFIX, ".xhtml");

            // Special Debug Output for Development
            if (isDebugMode())
                context.addInitParam(DEVELOPMENT, "true");

            context.addInitParam(REFRESH_PERIOD, String.valueOf(getRefreshPeriod()));

            if (isStrictMode()) {
                // Optional JSF-RI Parameters to Help Debug
                context.addInitParam(VALIDATE_XML, "true");
                context.addInitParam(VERIFY_OBJECTS, "true");
            }
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

        // Add spring mvc support along with FTC.
        stl.addServlet("spring-dispatcher", DispatcherServlet.class, "*.htm");
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