package com.bee32.sems.security.access.acl;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.Permission;

public class DenyACE
        extends ACE {

    private static final long serialVersionUID = 1L;

    public DenyACE(IPrincipal principal, Permission permission) {
        super(principal, permission);
    }

    @Override
    public boolean isAllowed() {
        return false;
    }

    @Override
    public boolean isDenied() {
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ 0xc6bd52b1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DenyACE))
            return false;
        return super._equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Deny %s: %s", getPrincipal(), getPermission());
    }

}
