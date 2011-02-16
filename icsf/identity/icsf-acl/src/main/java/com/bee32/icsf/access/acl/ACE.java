package com.bee32.icsf.access.acl;

import java.io.Serializable;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

public abstract class ACE
        implements IACL.Entry, Serializable {

    private static final long serialVersionUID = 1L;

    private final IPrincipal principal;
    private final Permission permission;

    public ACE(IPrincipal principal, Permission permission) {
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");
        this.principal = principal;
        this.permission = permission;
    }

    public IPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Override
    public boolean isDenied() {
        return !isAllowed();
    }

    @Override
    public int hashCode() {
        int hash = principal.hashCode();
        hash ^= permission.hashCode();
        return hash;
    }

    boolean _equals(Object obj) {
        if (!(obj instanceof ACE))
            return false;
        ACE that = (ACE) obj;
        if (!principal.equals(that.principal))
            return false;
        if (!principal.equals(that.permission))
            return false;
        return true;
    }

}
