package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

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
