package com.bee32.plover.web.faces.test;

public class FaceletsTestCaseTest
        extends FaceletsTestCase {

    @Override
    protected boolean isDebugMode() {
        return false;
    }

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    public static void main(String[] args)
            throws Exception {
        new FaceletsTestCaseTest().browseAndWait("/version.jsf");
    }

}
