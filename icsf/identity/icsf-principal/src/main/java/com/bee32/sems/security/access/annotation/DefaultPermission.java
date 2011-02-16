package com.bee32.sems.security.access.annotation;

import com.bee32.sems.security.IAuthority;
import com.bee32.sems.security.access.Permission;

final class DefaultPermission
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

    @Override
    public String getDisplayName() {
        // TODO Auto-generated method stub
        return null;
    }

}
