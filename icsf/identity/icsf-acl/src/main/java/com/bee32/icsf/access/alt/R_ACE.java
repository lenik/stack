package com.bee32.icsf.access.alt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@BatchSize(size = 100)
public class R_ACE
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    private String qualifiedName;
    private Principal principal;
    private String mode;

    R_ACE() {
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
        this.mode = mode;
    }

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
        this.qualifiedName = ResourceRegistry.qualify(resource);
    }

    @ManyToOne(optional = false)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Column(length = 32, nullable = false)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Transient
    public Permission getPermission() {
        return new Permission(mode);
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.mode = permission.toString();
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 0;
        result = prime * result + qualifiedName.hashCode();
        result = prime * result + principal.hashCode();
        result = prime * result + mode.hashCode();
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        R_ACE other = (R_ACE) otherEntity;

        if (!qualifiedName.equals(other.qualifiedName))
            return false;

        if (!mode.equals(other.mode))
            return false;

        if (!principal.equals(other.principal))
            return false;

        return true;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        // ap:foo.Bar: Tom +rw
        out.print(qualifiedName);
        out.print(": ");

        out.print(principal.getName());

        out.print(" +");
        out.print(mode);
    }

}
