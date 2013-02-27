package com.bee32.plover.faces.test;

import java.util.List;

import javax.faces.webapp.FacesServlet;
import javax.free.StringArray;

import org.eclipse.jetty.servlet.ServletHolder;
import org.primefaces.push.PushServlet;
import org.primefaces.webapp.filter.FileUploadFilter;

import com.bee32.plover.faces.ClassResourceResolver;
import com.bee32.plover.faces.FaceletsConfig;
import com.bee32.plover.faces.IFacesParameterNames;
import com.bee32.plover.servlet.rabbits.RabbitServletContextHandler;
import com.bee32.plover.servlet.test.C_Wac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class C_FaceletsWac
        extends C_Wac<FaceletsTestCase>
        implements IFacesParameterNames {

    boolean pushServerEnabled = true;

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    protected void configureServlets(ServletTestLibrary stl, FaceletsTestCase outer) {
        RabbitServletContextHandler context = stl.getServletContextHandler();
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

            // context.addInitParam(STATE_SAVING_METHOD, "partial");
            // context.addInitParam(STATE_SAVING_METHOD, "client");

            context.addInitParam(REFRESH_PERIOD, String.valueOf(outer.getRefreshPeriod()));

            if (outer.isStrictMode()) {
                // Optional JSF-RI Parameters to Help Debug
                context.addInitParam(RI_VALIDATE_XML, "true");
                context.addInitParam(RI_VERIFY_OBJECTS, "true");
                // Optional Myfaces-Impl Parameters.
                context.addInitParam(MF_VALIDATE_XML, "true");
                context.addInitParam(MF_STRICT_XHTML_LINKS, "true");
            }

            // By rewrite all validators for dynamic switching.
            // context.addInitParam(DISABLE_VALIDATOR, "true");
        }

        /** @see FaceletsStartupScl */

        // Faces Servlet, must be load-on-startup, but STL seems not support.
        ServletHolder facesServlet = stl.addServlet(FacesServlet.class, "*." + FaceletsConfig.extension);
        facesServlet.setInitOrder(0);

        // Primefaces configuration
        stl.addFilter(FileUploadFilter.class, "*." + FaceletsConfig.extension);
        context.addInitParam("primefaces.THEME", "#{guestPreferences.theme.name}");

        if (pushServerEnabled) {
            // Prime Push Server: must be load-on-startup.
            ServletHolder pushServlet = stl.addServlet(PushServlet.class, "/primepush/*");
            pushServlet.setInitOrder(1);
            pushServlet.setInitParameter("channels", "system, user, group");

            // This context-param is used by PushRenderer
            // context.addInitParam("primefaces.PUSH_SERVER_URL", //
            // // "ws://localhost:" + stl.getPort() + "/prime-push" //
            // "/primepush" // See: PloverPushRenderer.
            // );
        }
    }

}
