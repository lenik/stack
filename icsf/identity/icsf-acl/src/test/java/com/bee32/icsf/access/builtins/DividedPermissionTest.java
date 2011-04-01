package com.bee32.icsf.access.builtins;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DividedPermissionTest {

    static CakePermission owner = new CakePermission("owner", "Full access to cake");
    static CakePermission eat = (CakePermission) owner.divide("eat", "Eat permission", CakePermission.EAT);
    static CakePermission sell = (CakePermission) owner.divide("eat", "Eat permission", CakePermission.EAT);

    @Test
    public void testRootSelection()
            throws Exception {
        // CakePermission.fullPermission
    }
}
