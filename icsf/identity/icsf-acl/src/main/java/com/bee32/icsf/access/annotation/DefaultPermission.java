package com.bee32.icsf.access.annotation;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.authority.IAuthority;

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
