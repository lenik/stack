package com.bee32.sem.inventory;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMInventoryUnit.class)
public class SEMInventoryModuleTest
        extends SEMTestCase {

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMInventoryModuleTest().browseAndWait(//
                SEMInventoryModule.PREFIX + "/material/index-rich.jsf");
    }

}
