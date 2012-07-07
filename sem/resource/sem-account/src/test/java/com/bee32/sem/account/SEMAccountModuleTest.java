package com.bee32.sem.account;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMAccountUnit.class)
public class SEMAccountModuleTest
        extends SEMTestCase {

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 5;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMAccountModuleTest().browseAndWait(SEMAccountModule.PREFIX + "/receivable/");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }

}
