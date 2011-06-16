package com.bee32.sem.people;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.servlet.test.RabbitServletContext;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMPeopleUnit.class)
public class SEMPeopleModuleTest
        extends SEMTestCase {

    public SEMPeopleModuleTest() {
        super();
    }

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
        return "eva";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMPeopleModuleTest().browseAndWait("/people/personAdmin.jsf");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }


}
