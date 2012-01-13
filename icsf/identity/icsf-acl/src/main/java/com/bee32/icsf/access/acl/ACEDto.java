package com.bee32.icsf.access.acl;

import java.io.Serializable;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.PrincipalDto;

public class ACEDto
        implements Serializable {

    private static final long serialVersionUID = 1L;

    PrincipalDto principal;
    Permission permission;

    public ACEDto() {
    }

    public ACEDto(PrincipalDto principal, Permission permission) {
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");
        this.principal = principal;
        this.permission = permission;
    }

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permission = permission;
    }

}
