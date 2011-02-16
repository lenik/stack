package com.bee32.sems.security.access.builtins;

import com.bee32.sems.security.IAuthority;
import com.bee32.sems.security.access.Permission;

public class CompositePermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IAuthority getAuthority() {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return false;
    }

}
