package com.bee32.plover.web.faces.test;

public class FaceletsTestCaseTest
        extends FaceletsTestCase {

    public static void main(String[] args)
            throws Exception {
        new FaceletsTestCaseTest().wire().browseAndWait("/guess.jsf");
    }

}
