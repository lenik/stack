package com.bee32.plover.web.faces.test;

public class FaceletsTestCaseTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws Exception {
        new FaceletsTestCaseTest().browseAndWait("version.jsf");
    }

}
