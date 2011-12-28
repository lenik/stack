package com.bee32.icsf.access.dacl;

import java.io.Serializable;

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
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;

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

    public void setPermission(Permission allow) {
        if (allow == null)
            throw new NullPointerException("allow");
        this.permission = allow;
    }

    @Column
    @Index(name = "dace_admin")
    public Boolean getAdmin() {
        return permission.getAdmin();
    }

    public void setAdmin(Boolean f) {
        permission.setAdmin(f);
    }

    @Column
    @Index(name = "dace_readable")
    public Boolean getReadable() {
        return permission.getReadable();
    }

    public void setReadable(Boolean f) {
        permission.setReadable(f);
    }

    @Column
    @Index(name = "dace_writable")
    public Boolean getWritable() {
        return permission.getWritable();
    }

    public void setWritable(Boolean f) {
        permission.setWritable(f);
    }

    @Column
    @Index(name = "dace_executable")
    public Boolean getExecutable() {
        return permission.getExecutable();
    }

    public void setExecutable(Boolean f) {
        permission.setExecutable(f);
    }

    @Column
    @Index(name = "dace_listable")
    public Boolean getListable() {
        return permission.getListable();
    }

    public void setListable(Boolean f) {
        permission.setListable(f);
    }

    @Column
    @Index(name = "dace_creatable")
    public Boolean getCreatable() {
        return permission.getCreatable();
    }

    public void setCreatable(Boolean f) {
        permission.setCreatable(f);
    }

    @Column
    @Index(name = "dace_deletable")
    public Boolean getDeletable() {
        return permission.getDeletable();
    }

    public void setDeletable(Boolean f) {
        permission.setDeletable(f);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(dacl), //
                naturalId(principal));
    }

    @Override
    public ICriteriaElement selector(String prefix) {
        if (dacl == null)
            throw new NullPointerException("dacl");
        if (principal == null)
            throw new NullPointerException("principal");
        return selectors(//
                selector(prefix + "dacl", dacl), //
                selector(prefix + "principal", principal));
    }

}
