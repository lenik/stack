package com.bee32.icsf.access.builtins;

import com.bee32.icsf.access.Permission;

public class AllOfPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    private final Permission[] conjunction;

    public AllOfPermission(Permission... permissions) {
        super();
        if (permissions == null)
            throw new NullPointerException("permissions");
        this.conjunction = permissions;
    }

    public AllOfPermission(String name, Permission... permissions) {
        super(name);
        if (permissions == null)
            throw new NullPointerException("permissions");
        this.conjunction = permissions;
    }

    @Override
    public boolean implies(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        for (Permission p : conjunction)
            if (!p.implies(permission))
                return false;

        return true;
    }

}
