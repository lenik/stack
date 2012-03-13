package com.bee32.sem.makebiz;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMMakebizUnit.class)
public class SEMMakebizModuleTest
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
        new SEMMakebizModuleTest().browseAndWait(//
                SEMMakebizModule.PREFIX + "/order");
    }

}
