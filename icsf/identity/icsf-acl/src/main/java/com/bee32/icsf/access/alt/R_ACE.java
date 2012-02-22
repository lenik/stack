package com.bee32.icsf.access.alt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@Blue
@BatchSize(size = 100)
@SequenceGenerator(name = "idgen", sequenceName = "r_ace_seq", allocationSize = 1)
public class R_ACE
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    private String qualifiedName = "entity:";
    private Principal principal;
    private Permission permission = new Permission(0);

    public R_ACE() {
        super("ACE");
    }

    public R_ACE(Resource resource, Principal principal, Permission permission) {
        this(ResourceRegistry.qualify(resource), principal, permission.toString());
    }

    public R_ACE(Resource resource, Principal principal, String mode) {
        this(ResourceRegistry.qualify(resource), principal, mode);
    }

    public R_ACE(String qualifiedName, Principal principal, String mode) {
        super("ACE");

        if (qualifiedName == null)
            throw new NullPointerException("qualifiedName");
        if (principal == null)
            throw new NullPointerException("principal");
        if (mode == null)
            throw new NullPointerException("mode");

        setQualifiedName(qualifiedName);

        this.principal = principal;
        setMode(mode);
    }

    @NaturalId
    @Column(name = "qName", length = 100, nullable = false)
    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        if (qualifiedName == null)
            throw new NullPointerException("qualifiedName");

        if (qualifiedName.isEmpty()) {
            throw new IllegalArgumentException("Qualified name is empty.");
        } else {
            char ending = qualifiedName.charAt(qualifiedName.length() - 1);
            if (ending != '.' && ending != ':')
                throw new IllegalArgumentException("Qualified name should end with `:` or `.`.");
        }

        this.qualifiedName = qualifiedName;
    }

    @Transient
    public Resource getResource() {
        return ResourceRegistry.query(qualifiedName);
    }

    public void setResource(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");
        this.qualifiedName = ResourceRegistry.qualify(resource);
    }

    @NaturalId
    @ManyToOne(optional = false/* , fetch = FetchType.LAZY */)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    @Column(length = 32, nullable = false)
    public String getMode() {
        return permission.getModeString();
    }

    public void setMode(String mode) {
        if (mode == null)
            throw new NullPointerException("mode");
        this.permission = new Permission(mode);
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

    @Override
    protected Serializable naturalId() {
        return new IdComposite(qualifiedName, naturalId(principal));
    }

    @Override
    public ICriteriaElement selector(String prefix) {
        if (qualifiedName == null)
            throw new NullPointerException("qualifiedName");
        if (principal == null)
            throw new NullPointerException("principal");
        return selectors(//
                new Equals(prefix + "qualifiedName", qualifiedName), //
                selector(prefix + "principal", principal));
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        // ap:foo.Bar: Tom +rw
        out.print(qualifiedName);
        out.print(": ");

        out.print(principal.getName());

        out.print(" +");
        out.print(permission.getModeString());
    }

    public static R_ACE adminApAll = new R_ACE("ap:", User.admin, "surwcdx");
    public static R_ACE adminEntityAll = new R_ACE("entity:", User.admin, "surwcdx");

}
