package com.bee32.sems.purchase;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.purchase.SEMPurchaseModule;
import com.bee32.sem.purchase.SEMPurchaseUnit;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMPurchaseUnit.class)
public class SEMPurchaseModuleTest
        extends SEMTestCase {

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
        new SEMPurchaseModuleTest().browseAndWait(SEMPurchaseModule.PREFIX + "/makeOrder/");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }

}
