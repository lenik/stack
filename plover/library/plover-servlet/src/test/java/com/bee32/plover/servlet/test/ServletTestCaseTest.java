package com.bee32.plover.servlet.test;

import java.io.IOException;

public class ServletTestCaseTest
        extends ServletTestCase {

    public static void main(String[] args)
            throws IOException {
        new ServletTestCaseTest().unit().mainLoop();
    }

}
