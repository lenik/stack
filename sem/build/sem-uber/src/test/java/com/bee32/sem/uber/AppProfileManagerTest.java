package com.bee32.sem.uber;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.AppProfileManager;

public class AppProfileManagerTest
        extends Assert {

    @Test
    public void testScan() {
        for (String pn : AppProfileManager.getProfileMap().keySet()) {
            System.out.println(pn);
        }
    }

}
