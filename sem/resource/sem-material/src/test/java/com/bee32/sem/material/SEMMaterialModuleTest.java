package com.bee32.sem.material;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMMaterialUnit.class)
public class SEMMaterialModuleTest
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
        new SEMMaterialModuleTest().browseAndWait(//
                // SEMInventoryModule.PREFIX + "/material/" //
                SEMMaterialModule.PREFIX + "/material/" //
                );
    }

}
