package com.bee32.sems.security.access.acl;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.Permission;

public class AllowACE
        extends ACE {

    private static final long serialVersionUID = 1L;

    public AllowACE(IPrincipal principal, Permission permission) {
        super(principal, permission);
    }

    @Override
    public boolean isAllowed() {
        return true;
    }

    @Override
    public boolean isDenied() {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ 0xe7ab598a;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AllowACE))
            return false;
        return super._equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Allow %s: %s", getPrincipal(), getPermission());
    }

}
