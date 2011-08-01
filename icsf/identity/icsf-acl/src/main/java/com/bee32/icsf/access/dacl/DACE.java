package com.bee32.icsf.access.dacl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "dace_seq", allocationSize = 1)
public class DACE
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    DACL dacl;
    Principal principal;
    Permission permission;

    public DACE() {
        super();
        this.permission = new Permission(0);
    }

    public DACE(DACL dacl, Principal principal, Permission permission) {
        super();

        if (dacl == null)
            throw new NullPointerException("dacl");
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");

        this.dacl = dacl;
        this.principal = principal;
        this.permission = permission;
    }

    @NaturalId
    @ManyToOne
    @JoinColumn(nullable = false)
    public DACL getDacl() {
        return dacl;
    }

    public void setDacl(DACL dacl) {
        if (dacl == null)
            throw new NullPointerException("dacl");
        this.dacl = dacl;
    }

    @NaturalId
    @ManyToOne
    @JoinColumn(nullable = false)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    @Transient
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permission = permission;
    }

    @Column(nullable = false)
    @Index(name = "dace_readable")
    public boolean isReadable() {
        return permission.isReadable();
    }

    @Column(nullable = false)
    @Index(name = "dace_writable")
    public boolean isWritable() {
        return permission.isWritable();
    }

    @Column(nullable = false)
    @Index(name = "dace_executable")
    public boolean isExecutable() {
        return permission.isExecutable();
    }

    @Column(nullable = false)
    @Index(name = "dace_listable")
    public boolean isListable() {
        return permission.isListable();
    }

    @Column(nullable = false)
    @Index(name = "dace_creatable")
    public boolean isCreatable() {
        return permission.isCreatable();
    }

    @Column(nullable = false)
    @Index(name = "dace_deletable")
    public boolean isDeletable() {
        return permission.isDeletable();
    }

    @Column(nullable = false)
    @Index(name = "dace_admin")
    public boolean isAdmin() {
        return permission.isAdmin();
    }

    public void setReadable(boolean f) {
        permission.setReadable(f);
    }

    public void setWritable(boolean f) {
        permission.setWritable(f);
    }

    public void setExecutable(boolean f) {
        permission.setExecutable(f);
    }

    public void setListable(boolean f) {
        permission.setListable(f);
    }

    public void setCreatable(boolean f) {
        permission.setCreatable(f);
    }

    public void setDeletable(boolean f) {
        permission.setDeletable(f);
    }

    public void setAdmin(boolean f) {
        permission.setAdmin(f);
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        DACE o = (DACE) other;

        if (!dacl.equals(o.dacl))
            return false;

        if (!principal.equals(o.principal))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += dacl.hashCode();
        hash += principal.hashCode();
        return hash;
    }

}
