package com.bee32.sem.world;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class SEMWorldTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMWorldTest().browseAndWait();
    }

}
