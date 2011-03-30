package com.bee32.icsf.access.alt;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.Permissions;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@BatchSize(size = 100)
public class R_ACE
        extends EntityBean<Long>
        implements IACL.Entry {

    private static final long serialVersionUID = 1L;

    private R_ACL acl;
    private IPrincipal principal;
    private String permissionName;
    private boolean allowed;

    R_ACE() {
        super("ACE");
    }

    public R_ACE(R_ACL acl, IPrincipal principal, Permission permission, boolean allowed) {
        this(acl, principal, permission.getName(), allowed);
    }

    public R_ACE(R_ACL acl, IPrincipal principal, String permissionName, boolean allowed) {
        super("ACE");

        if (acl == null)
            throw new NullPointerException("acl");
        if (principal == null)
            throw new NullPointerException("principal");
        if (permissionName == null)
            throw new NullPointerException("permissionName");

        this.acl = acl;
        this.principal = principal;
        this.permissionName = permissionName;
        this.allowed = allowed;
    }

    @ManyToOne(optional = false)
    public R_ACL getAcl() {
        return acl;
    }

    public void setAcl(R_ACL acl) {
        this.acl = acl;
    }

    @ManyToOne(targetEntity = Principal.class, optional = false)
    public IPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(IPrincipal principal) {
        this.principal = principal;
    }

    @Column(length = 100, nullable = false)
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Basic
    public boolean isAllowed() {
        return allowed;
    }

    @Transient
    @Override
    public boolean isDenied() {
        return false;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Transient
    public Permission getPermission() {
        return Permissions.getPermission(permissionName);
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permissionName = permission.getName();
    }

    public IACL.Entry toACE() {
        // IACL.Entry ace = new ACE(principal, getPermission(), allowed);
        IACL.Entry ace = this;
        return ace;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 0;
        result = prime * result + (allowed ? 1231 : 1237);
        result = prime * result + ((permissionName == null) ? 0 : permissionName.hashCode());
        result = prime * result + ((principal == null) ? 0 : principal.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        R_ACE other = (R_ACE) otherEntity;

        if (allowed != other.allowed)
            return false;

        if (!permissionName.equals(other.permissionName))
            return false;

        if (!principal.equals(other.principal))
            return false;

        return true;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        out.print(principal);
        if (allowed)
            out.print(" + ");
        else
            out.print(" - ");
        out.print(permissionName);
    }

}
