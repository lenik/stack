package com.bee32.plover.test.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServletTestCaseTest
        extends ServletTestCase {

    @Test
    public void testGetURL()
            throws Exception {
        addServlet(HelloServlet.class, "/hello");
        String uri = "http://localhost:" + getPort() + "/hello?name=foo";
        String content = get(uri).getContent();
        assertEquals("hello, foo\n", content);
    }

}
