package com.bee32.plover.test.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloServletTest
        extends ServletTestCase {

    @Test
    public void testHello()
            throws Exception {
        addServlet(HelloServlet.class, "/hello");
        String content = get("/hello?name=foo").getContent();
        assertEquals("hello, foo\n", content);
    }

}
