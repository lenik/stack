package com.bee32.sem.module;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMBaseUnit.class)
public class Stc1
        extends SEMTestCase {

    @Override
    protected boolean isStrictMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    @Override
    protected String getLoggedInUser() {
        return null;
    }

    public static void main(String[] args)
            throws IOException {
        new Stc1().browseAndWait("logincheck.jsf");
    }

}
