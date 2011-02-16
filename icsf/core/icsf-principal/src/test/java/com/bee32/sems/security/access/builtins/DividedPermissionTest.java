package com.bee32.sems.security.access.builtins;

import org.junit.Test;

import com.bee32.sems.security.IAuthority;

class CakePermission
        extends DividedPermission {

    private static final long serialVersionUID = 1L;

    public CakePermission(String name, String description) {
        super(IAuthority.ROOT, name, description);
    }

    protected CakePermission(DividedPermission parent, String name, String description, int... ranges) {
        super(parent, IAuthority.ROOT, name, description, ranges);
    }

    @Override
    protected CakePermission newInstance(DividedPermission parent, String name, String description, int... ranges) {
        return new CakePermission(parent, name, description, ranges);
    }

    public static final int EAT = 1;
    public static final int SELL = 2;
    public static final int CREAM_PART = 3;
    public static final int EGG_PART = 4;
    public static final int BREAD_PART = 5;

    @Override
    protected String getRangeName(int range) {
        switch (range) {
        case EAT:
            return "Eat";
        case SELL:
            return "Sell";
        case CREAM_PART:
            return "Cream";
        case EGG_PART:
            return "Egg";
        case BREAD_PART:
            return "Break";
        }
        return null;
    }

}

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
