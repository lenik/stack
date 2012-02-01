package com.bee32.sem.inventory;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class CtrlTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new CtrlTest().browseAndWait("/login.xhtml");
    }

}
