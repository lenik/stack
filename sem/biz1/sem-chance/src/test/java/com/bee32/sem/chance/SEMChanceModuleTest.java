package com.bee32.sem.chance;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMChanceUnit.class)
public class SEMChanceModuleTest
        extends SEMTestCase {

    static {
        Locale.setDefault(Locale.CHINA);
    }

    public SEMChanceModuleTest() {
        super();
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMChanceModuleTest().browseAndWait(SEMChanceModule.PREFIX + "/chance/");
    }

}
