package com.bee32.sem.mail;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMMailUnit.class)
public class SEMMailModuleTest
        extends SEMTestCase {

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
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

        new SEMMailModuleTest().browseAndWait(//
// MailFilterController.PREFIX + "index.do"//
                SEMMailModule.PREFIX + "/mailbox/index-rich.jsf"//
                );
    }

}
