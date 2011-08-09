package com.bee32.plover.restful.test;

import com.bee32.plover.restful.DispatchServlet;
import com.bee32.plover.restful.RESTfulConfig;
import com.bee32.plover.servlet.test.AbstractWebAppConfigurer;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class RESTfulWac
        extends AbstractWebAppConfigurer {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        // stl.addFilter(DispatchFilter.class, "/*", 0);

        // Also enable Restful service.
        stl.addServlet(DispatchServlet.class, RESTfulConfig.preferredPrefix + "/*");
    }

}
