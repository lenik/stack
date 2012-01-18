package com.bee32.sem.uber;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMUberUnit.class)
public class SEMUberModuleTest
        extends SEMTestCase {

    static {
        Locale zh_CN = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh_CN);
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected String getLoggedInUser() {
        return "eva";
    }

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMUberModuleTest().browseAndWait();
    }

}
