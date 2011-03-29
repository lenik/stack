package com.bee32.icsf.access.acl;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Transient;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

public class ACE
        implements IACL.Entry, Serializable {

    private static final long serialVersionUID = 1L;

    private IPrincipal principal;
    private Permission permission;
    private boolean allowed;

    public ACE(IPrincipal principal, Permission permission, boolean allowed) {
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");
        this.principal = principal;
        this.permission = permission;
        this.allowed = allowed;
    }

    public IPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Basic(optional = false)
    public boolean isAllowed() {
        return allowed;
    }

    @Transient
    @Override
    public boolean isDenied() {
        return !allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((permission == null) ? 0 : permission.hashCode());
        result = prime * result + ((principal == null) ? 0 : principal.hashCode());
        if (allowed)
            result *= prime;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj.getClass() != ACE.class)
            return false;

        ACE other = (ACE) obj;

        if (!permission.equals(other.permission))
            return false;

        if (!principal.equals(other.principal))
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(principal);
        if (allowed)
            sb.append(" + ");
        else
            sb.append(" - ");
        sb.append(permission);
        return sb.toString();
    }

}
