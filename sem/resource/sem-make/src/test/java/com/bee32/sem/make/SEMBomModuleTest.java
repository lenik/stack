package com.bee32.sem.make;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.make.SEMBomModule;
import com.bee32.sem.make.SEMBomUnit;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMBomUnit.class)
public class SEMBomModuleTest
        extends SEMTestCase {

    static {
        Locale.setDefault(Locale.CHINA);
    }

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMBomModuleTest().browseAndWait(//
                SEMBomModule.PREFIX + "/part");
    }

}
