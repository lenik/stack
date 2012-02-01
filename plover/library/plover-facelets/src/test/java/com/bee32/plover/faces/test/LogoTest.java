package com.bee32.plover.faces.test;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class LogoTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    public static void main(String[] args)
            throws Exception {
        new LogoTest().browseAndWait("ftc/logo.jsf");
    }

}
