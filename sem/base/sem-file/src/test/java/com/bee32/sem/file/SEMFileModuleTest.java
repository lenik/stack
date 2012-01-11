package com.bee32.sem.file;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMFileUnit.class)
public class SEMFileModuleTest
        extends SEMTestCase {

    public SEMFileModuleTest() {
        super();
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMFileModuleTest().browseAndWait(SEMFileModule.PREFIX + "/file/");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }

}
