package com.bee32.sem.file.web;

import org.junit.Assert;
import org.junit.Test;

public class UserFileServletTest
        extends Assert {

    @Test
    public void testName() {
        UserFileServlet servlet = new UserFileServlet();
        assertEquals("user-file", servlet.getName());
    }

}
