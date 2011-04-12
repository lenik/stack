package com.bee32.sem.process;

import java.io.IOException;

import org.junit.Test;

import com.bee32.plover.restful.test.RESTfulTestCase;

public class SemProcessModuleTest
        extends RESTfulTestCase {

    @Test
    public void testUser()
            throws Exception {

        String loc = "http://localhost:" + stl.getPort() + PREFIX + "/";
        System.out.println(loc);
    }

    public static void main(String[] args)
            throws IOException {
        new SemProcessModuleTest().browseAndWait(SEMProcessModule.class);
    }

}
