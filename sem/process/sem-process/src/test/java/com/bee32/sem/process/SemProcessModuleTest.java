package com.bee32.sem.process;

import org.junit.Test;

import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.test.RestfulTesterLibrary;
import com.bee32.plover.test.AssembledTestCase;

public class SemProcessModuleTest
        extends AssembledTestCase {

    RestfulTesterLibrary rtl;

    public SemProcessModuleTest() {
        install(rtl = new RestfulTesterLibrary());
    }

    @Test
    public void testUser()
            throws Exception {

        // addFilter(DispatchFilter.class, "/", 3);
        rtl.addServlet(DispatchFilter.class, "/");

        String loc = "http://localhost:" + rtl.getPort();
        System.out.println(loc);

        Thread.sleep(10000000);
    }

}
