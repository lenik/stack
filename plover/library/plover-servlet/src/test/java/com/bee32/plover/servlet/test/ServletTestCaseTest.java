package com.bee32.plover.servlet.test;


public class ServletTestCaseTest
        extends ServletTestCase {

    @Override
    protected void configureServlets() {
    }

    public static void main(String[] args)
            throws Exception {
        new ServletTestCaseTest().browseAndWait();
    }

}
