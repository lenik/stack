package com.bee32.plover.servlet.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.servlet.util.HelloServlet;

public class ServletTesterLibraryTest
        extends ServletTestLibrary {

    @Test
    public void testGetURL()
            throws Exception {
        addServlet(HelloServlet.class, "/hello");
        String uri = "http://localhost:" + getPort() + "/hello?name=foo";
        String content = httpGet(uri).getContent();
        assertEquals("hello, foo\n", content);
    }

    @Test
    public void testGetEmptyParam()
            throws Exception {
        addServlet(HelloServlet.class, "/hello");
        String uri = "http://localhost:" + getPort() + "/hello?hack&name=foo";
        String content = httpGet(uri).getContent();
        assertEquals("hey, hacker foo\n", content);
    }

}
