package com.bee32.plover.arch;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test should be run under -uber app.
 */
@Deprecated
public class AppProfileManagerTest
        extends Assert {

    @Test
    public void testScan() {
        Map<String, IAppProfile> profileMap = AppProfileManager.getProfileMap();
        for (String pn : profileMap.keySet()) {
            System.out.println(pn);
        }
    }

}
