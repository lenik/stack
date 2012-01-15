package com.bee32.icsf.access.acl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.collections15.Transformer;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;

@Entity(name = "acl_entry")
@SequenceGenerator(name = "idgen", sequenceName = "acl_entry_seq", allocationSize = 1)
public class ACLEntry
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    ACL acl;
    Principal principal;
    Permission permission;

    public ACLEntry() {
        this.permission = new Permission(0);
    }

    public ACLEntry(ACL acl, Principal principal, Permission permission) {
        super();

        if (acl == null)
            throw new NullPointerException("acl");
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");

        this.acl = acl;
        this.principal = principal;
        this.permission = permission;
    }

    @NaturalId
    @ManyToOne
    @JoinColumn(name = "acl", nullable = false)
    public ACL getACL() {
        return acl;
    }

    public void setACL(ACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");
        this.acl = acl;
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

    @Index(name = "##_admin")
    @Column
    public Boolean getAdmin() {
        return permission.getAdmin().getTristateBoolean();
    }

    public void setAdmin(Boolean f) {
        permission.setAdmin(f);
    }

    @Index(name = "##_user")
    @Column
    public Boolean getUser() {
        return permission.getUser().getTristateBoolean();
    }

    public void setUser(Boolean f) {
        permission.setUser(f);
    }

    @Index(name = "##_readable")
    @Column
    public Boolean getReadable() {
        return permission.getRead().getTristateBoolean();
    }

    public void setReadable(Boolean f) {
        permission.setReadable(f);
    }

    @Index(name = "##_writable")
    @Column
    public Boolean getWritable() {
        return permission.getWrite().getTristateBoolean();
    }

    public void setWritable(Boolean f) {
        permission.setWritable(f);
    }

    @Index(name = "##_executable")
    @Column
    public Boolean getExecutable() {
        return permission.getExecute().getTristateBoolean();
    }

    public void setExecutable(Boolean f) {
        permission.setExecutable(f);
    }

    @Index(name = "##_creatable")
    @Column
    public Boolean getCreatable() {
        return permission.getCreate().getTristateBoolean();
    }

    public void setCreatable(Boolean f) {
        permission.setCreatable(f);
    }

    @Index(name = "##_deletable")
    @Column
    public Boolean getDeletable() {
        return permission.getDelete().getTristateBoolean();
    }

    public void setDeletable(Boolean f) {
        permission.setDeletable(f);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(acl), //
                naturalId(principal));
    }

    @Override
    public ICriteriaElement selector(String prefix) {
        if (acl == null)
            throw new NullPointerException("acl");
        if (principal == null)
            throw new NullPointerException("principal");
        return selectors(//
                selector(prefix + "acl", acl), //
                selector(prefix + "principal", principal));
    }

}

class EntryPermissionTransformer
        implements Transformer<ACLEntry, Permission> {

    @Override
    public Permission transform(ACLEntry input) {
        if (input == null)
            return null;
        else
            return input.permission;
    }

    public static final EntryPermissionTransformer INSTANCE = new EntryPermissionTransformer();

}