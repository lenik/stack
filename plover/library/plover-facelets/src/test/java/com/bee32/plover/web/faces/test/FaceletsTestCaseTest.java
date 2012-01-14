package com.bee32.plover.web.faces.test;

import java.util.Locale;

public class FaceletsTestCaseTest
        extends FaceletsTestCase {

    static {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    protected int getRefreshPeriod() {
        return 0;
    }

    public static void main(String[] args)
            throws Exception {
        new FaceletsTestCaseTest().browseAndWait("test/color.jsf");
    }

}
