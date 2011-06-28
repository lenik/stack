package com.bee32.icsf.access.flatacl;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityAuto;

@Entity
public class FlatACE
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    FlatACL dacl;
    Principal principal;
    Permission permission;

    @NaturalId
    @ManyToMany
    @JoinColumn(nullable = false)
    public FlatACL getDACL() {
        return dacl;
    }

    public void setDACL(FlatACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");
        this.dacl = acl;
    }

    @NaturalId
    @ManyToMany
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}
