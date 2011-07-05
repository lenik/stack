package com.bee32.sem.module;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMBaseUnit.class)
public class Stc1
        extends SEMTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new Stc1().browseAndWait("logincheck.jsf");
    }

}
