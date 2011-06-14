package com.bee32.sem.frame;

import java.io.IOException;

import com.bee32.plover.web.faces.test.FaceletsTestCase;

public class SEMFrameModuleTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMFrameModuleTest().browseAndWait("test/firstView.do");
    }

}
