package com.bee32.plover.restful.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.test.ServletTestLibrary;

public class SharedDispatcherServletTest
        extends ServletTestLibrary {

    public SharedDispatcherServletTest() {
        addServlet("dispatcher", SharedDispatcherServlet.class, "/");
        setHintRoots("WEB-INF/dispatcher-servlet.xml");
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
