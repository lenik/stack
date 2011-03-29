package com.bee32.icsf.access.builtins;

import com.bee32.icsf.access.Permission;

public class AnyOfPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    private final Permission[] disjunction;

    public AnyOfPermission(Permission... permissions) {
        super();
        if (permissions == null)
            throw new NullPointerException("permissions");
        this.disjunction = permissions;
    }

    public AnyOfPermission(String name, Permission... permissions) {
        super(name);
        if (permissions == null)
            throw new NullPointerException("permissions");
        this.disjunction = permissions;
    }

    @Override
    public boolean implies(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        for (Permission p : disjunction)
            if (p.implies(permission))
                return true;

        return false;
    }

}
