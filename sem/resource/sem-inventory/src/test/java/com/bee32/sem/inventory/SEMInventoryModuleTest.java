package com.bee32.sem.inventory;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMInventoryUnit.class)
public class SEMInventoryModuleTest
        extends SEMTestCase {

    static {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    }

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
        return 5;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMInventoryModuleTest().browseAndWait(//
                // SEMInventoryModule.PREFIX + "/material/" //
                SEMInventoryModule.PREFIX + "/take/?subject=TK_O" //
                );
    }
}
