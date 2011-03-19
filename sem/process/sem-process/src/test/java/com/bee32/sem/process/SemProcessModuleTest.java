package com.bee32.sem.process;

import org.junit.Test;

import com.bee32.plover.restful.test.RestfulTestCase;

public class SemProcessModuleTest
        extends RestfulTestCase {

    @Test
    public void testUser()
            throws Exception {

        String loc = "http://localhost:" + rtl.getPort();
        System.out.println(loc);

        Thread.sleep(10000000);
    }

}
