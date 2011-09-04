package com.bee32.plover.web.faces.test;

import java.util.List;

import javax.faces.webapp.FacesServlet;
import javax.free.StringArray;

import org.mortbay.jetty.servlet.ServletHolder;
import org.primefaces.webapp.filter.FileUploadFilter;

import com.bee32.plover.servlet.rabbits.RabbitServletContext;
import com.bee32.plover.servlet.test.OuterWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.web.faces.ClassResourceResolver;
import com.bee32.plover.web.faces.FaceletsConfig;
import com.bee32.plover.web.faces.FacesConstants;

public class FaceletsWac
        extends OuterWac<FaceletsTestCase>
        implements FacesConstants {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    protected void configureServlets(ServletTestLibrary stl, FaceletsTestCase outer) {
        RabbitServletContext context = stl.getServletContext();
        {
            List<String> taglibs = outer.taglibs;
            if (!taglibs.isEmpty()) {
                String libraries = StringArray.join(";", taglibs);
                context.addInitParam(LIBRARIES, libraries);
            }

            context.addInitParam(RESOURCE_RESOLVER, ClassResourceResolver.class.getName());

            // - Use Documents Saved as *.xhtml
            context.addInitParam(DEFAULT_SUFFIX, ".xhtml");

            // Special Debug Output for Development
            if (outer.isDebugMode()) {
                context.addInitParam(DEVELOPMENT, "true");
                context.addInitParam(PROJECT_STAGE, "Development");
            }

            context.addInitParam(REFRESH_PERIOD, String.valueOf(outer.getRefreshPeriod()));

            if (outer.isStrictMode()) {
                // Optional JSF-RI Parameters to Help Debug
                context.addInitParam(RI_VALIDATE_XML, "true");
                context.addInitParam(RI_VERIFY_OBJECTS, "true");
                // Optional Myfaces-Impl Parameters.
                context.addInitParam(MF_VALIDATE_XML, "true");
                context.addInitParam(MF_STRICT_XHTML_LINKS, "true");
            }
        }

        /** @see FaceletsStartupScl */

        // Faces Servlet, must be load-on-startup, but STL seems not support.
        ServletHolder facesServlet = stl.addServlet(FacesServlet.class, "*." + FaceletsConfig.extension);
        facesServlet.setInitOrder(0);

        stl.addFilter(FileUploadFilter.class, "*." + FaceletsConfig.extension, 0);
    }

}
