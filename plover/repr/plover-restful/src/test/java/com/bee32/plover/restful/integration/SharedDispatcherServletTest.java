package com.bee32.plover.restful.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.test.ServletTesterLibrary;

public class SharedDispatcherServletTest
        extends ServletTesterLibrary {

    @Override
    protected void configureServlets()
            throws Exception {
        addServlet("dispatcher", SharedDispatcherServlet.class, "/");

        setRootResourceFile("WEB-INF/dispatcher-servlet.xml");
    }

    /**
     * Set on a thread different with get.
     *
     * So, you can't get shared context thru dispatcher-servlet.
     */
    @Test
    @Ignore
    public void testContext()
            throws Exception {
        ApplicationContext context = SharedDispatcherServlet.getThreadApplicationContext();
        System.out.println(context);
    }

}
