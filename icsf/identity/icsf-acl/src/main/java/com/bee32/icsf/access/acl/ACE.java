package com.bee32.icsf.access.acl;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;

public class ACE
        extends EntityBean<Long>
        implements IACL.Entry, Serializable {

    private static final long serialVersionUID = 1L;

    private IPrincipal principal;
    private Permission permission;
    private boolean allowed;

    public ACE() {
    }

    @ManyToOne(targetEntity = Principal.class)
    public IPrincipal getPrincipal() {
        return principal;
    }

    @Column(nullable = false, length = 50)
    public String getPermissionId() {

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
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((permission == null) ? 0 : permission.hashCode());
        result = prime * result + ((principal == null) ? 0 : principal.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        ACE other = (ACE) otherEntity;

        if (!permission.equals(other.permission))
            return false;

        if (!principal.equals(other.principal))
            return false;

        return true;
    }

}
