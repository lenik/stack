package com.bee32.plover.servlet.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.servlet.test.ServletTestCase;
import com.bee32.plover.servlet.util.HelloServlet;


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
