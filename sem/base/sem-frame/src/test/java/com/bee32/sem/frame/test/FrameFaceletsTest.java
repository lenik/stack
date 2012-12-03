package com.bee32.sem.frame.test;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.Using;

@Using(PloverORMUnit.class)
public class FrameFaceletsTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new FrameFaceletsTest().browseAndWait("test/person-lv.jsf");
    }

}
