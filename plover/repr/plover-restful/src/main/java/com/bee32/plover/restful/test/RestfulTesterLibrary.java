package com.bee32.plover.restful.test;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bee32.plover.inject.LegacyContext;
import com.bee32.plover.orm.context.TxContext;
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.servlet.test.ServletTesterLibrary;

public class RestfulTesterLibrary
        extends ServletTesterLibrary {

    /**
     * Using following contexts by default
     * <ul>
     * <li>LegacyContext
     * <li>TxContext
     * </ul>
     */
    public RestfulTesterLibrary() {
        this(new TxContext(new LegacyContext()).getApplicationContext());
    }

    public RestfulTesterLibrary(ApplicationContext applicationContext) {
        if (applicationContext != null) {
            String wacAttr = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
            setAttribute(wacAttr, applicationContext);
        }
    }

    /**
     * The default servlet must be configured.
     *
     * See http://crudaloola.wordpress.com/2008/10/22/how.
     */
    @Override
    protected void configureServlets()
            throws Exception {

        addFilter(DispatchFilter.class, "/*", 0);

        // The default servlet must be existed.
        // Otherwise, the filter won't work.
        addServlet(DefaultServlet.class, "/");

        // String wacAttr=WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    }

}
