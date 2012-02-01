package com.bee32.sem.frame;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.Using;

@Using(PloverORMUnit.class)
public class SEMFrameModuleTest
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMFrameModuleTest().browseAndWait("test/listview.jsf");
    }

}
