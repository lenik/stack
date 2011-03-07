package com.bee32.plover.servlet.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.servlet.test.ServletTesterLibrary;

public class HelloServletTest
        extends ServletTesterLibrary {

    @Test
    public void testHello()
            throws Exception {
        addServlet(HelloServlet.class, "/hello");
        String content = httpGet("/hello?name=foo").getContent();
        assertEquals("hello, foo\n", content);
    }

}
