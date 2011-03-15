package com.bee32.plover.restful.test;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.cref.ContextRefs;
import com.bee32.plover.inject.cref.StdScanTestContext;
import com.bee32.plover.inject.cref.TemplateTestContext;
import com.bee32.plover.orm.context.TxContext;
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.context.SimpleApplicationContextUtil;
import com.bee32.plover.servlet.test.ServletTesterLibrary;

public class RestfulTesterLibrary
        extends ServletTesterLibrary {

    /**
     * <ul>
     * <li> {@link StdScanTestContext}
     * <li> {@link TemplateTestContext}
     * </ul>
     */
    public static ContextRef defaultContextRef = new TxContext(ContextRefs.TEMPLATE_TEST);

    /**
     * @see #defaultContextRef
     */
    public RestfulTesterLibrary() {
        this(defaultContextRef.getApplicationContext());
    }

    public RestfulTesterLibrary(ApplicationContext applicationContext) {
        if (applicationContext != null) {
            String key = SimpleApplicationContextUtil.rootApplicationContextKey;
            setAttribute(key, applicationContext);
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
