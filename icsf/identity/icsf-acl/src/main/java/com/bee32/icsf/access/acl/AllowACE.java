package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

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
