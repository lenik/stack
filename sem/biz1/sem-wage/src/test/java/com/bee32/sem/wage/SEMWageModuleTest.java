package com.bee32.sem.wage;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;
import com.bee32.sem.wage.SEMWageModule;
import com.bee32.sem.wage.SEMWageUnit;

@Using(SEMWageUnit.class)
public class SEMWageModuleTest
        extends SEMTestCase {
    public SEMWageModuleTest() {
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
        new SEMWageModuleTest().browseAndWait(SEMWageModule.PREFIX + "/attendancem/");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }
}
