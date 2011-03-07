package com.bee32.plover.restful.book;

import java.awt.Desktop;
import java.net.URI;

import org.junit.Test;

import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.servlet.test.ServletTestCase;

public class BookModuleTest
        extends ServletTestCase {

    @Test
    public void testUser()
            throws Exception {
        // addFilter(DispatchFilter.class, "/", 3);
        addServlet(DispatchFilter.class, "/");

        String loc = "http://localhost:" + getPort();
        System.out.println(loc);

        URI uri = new URI(loc);
        Desktop.getDesktop().browse(uri);

        Thread.sleep(10000000);
    }

}
