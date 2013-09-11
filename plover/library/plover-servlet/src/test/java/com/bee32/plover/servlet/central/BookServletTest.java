package com.bee32.plover.servlet.central;

import org.junit.Assert;
import org.junit.Test;

public class BookServletTest
        extends Assert {

    @Test
    public void testAutoName() {
        BookServlet servlet = new BookServlet();
        assertEquals("book", servlet.getName());
    }

}
