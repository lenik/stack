package com.bee32.sem.track;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMTrackUnit.class)
public class SEMTrackModuleTest
        extends SEMTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMTrackModuleTest().browseAndWait(SEMTrackModule.PREFIX_ + "issue/");
    }

}
