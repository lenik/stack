package com.bee32.plover.restful.test;

import org.springframework.web.context.WebApplicationContext;

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
    // public static ContextRef defaultContextRef = new TxContext(ContextRefs.TEMPLATE_TEST);

    /**
     * Should configure application-context using {@link RestfulTestCase.LocalRTL}
     */
    public RestfulTesterLibrary(Class<?> baseClass) {
        super(baseClass);
        // this(defaultContextRef.getApplicationContext());
    }

    public RestfulTesterLibrary(Class<?> baseClass, WebApplicationContext applicationContext) {
        super(baseClass);
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

        // String wacAttr=WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    }

}
