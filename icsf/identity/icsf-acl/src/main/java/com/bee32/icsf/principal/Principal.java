package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.tree.TreeEntityAuto;

@Entity
@Green
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "steoro", length = 3)
public abstract class Principal
        extends TreeEntityAuto<Integer, Principal>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    public static final int NAME_MAXLEN = 16;

    String name;
    String fullName;

    public Principal() {
        this.name = null;
    }

    public Principal(String name) {
        setName(name);
    }

    public Principal(String name, String fullName) {
        setName(name);
        setFullName(fullName);
    }

    @NaturalId
    @Column(length = NAME_MAXLEN, nullable = false)
    @Override
    public String getName() {
        return name;
    }

    /**
     * You can't change the name of a principal.
     * <p>
     * Names are automaticlly converted to lower-case.
     *
     * @param name
     *            Non-<code>null</code> name to set.
     * @see #NAME_MAXLEN
     */
    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");

        name = name.toLowerCase(Locale.ROOT);

        if (this.name != null && !this.name.equals(name))
            throw new IllegalStateException("Principal.name is not mutable: " + this.name + " -> " + name);

        this.name = name;
    }

    @Column(length = 50)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Transient
    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty())
            return fullName;
        return name;
    }

    @Override
    public Principal detach() {
        super.detach();
        return this;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        if (this.equals(principal))
            return true;

        return false;
    }

    @Override
    public boolean impliesOneOf(Collection<? extends IPrincipal> principals) {
        if (principals != null)
            for (IPrincipal principal : principals)
                if (implies(principal))
                    return true;

        return false;
    }

    @Override
    public boolean impliesAllOf(Collection<? extends IPrincipal> principals) {
        if (principals != null)
            for (IPrincipal principal : principals)
                if (implies(principal))
                    return false;

        return true;
    }

    @Override
    public boolean impliedByOneOf(Collection<? extends IPrincipal> principals) {
        if (principals != null)
            for (IPrincipal principal : principals)
                if (principal.implies(this))
                    return true;

        return false;
    }

    @Override
    public boolean impliedByAllOf(Collection<? extends IPrincipal> principals) {
        if (principals != null)
            for (IPrincipal principal : principals)
                if (!principal.implies(this))
                    return false;

        return true;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Integer> other) {
        Principal o = (Principal) other;

        if (this.name == null || o.name == null)
            return false;

        return name.equals(o.name);
    }

    @Override
    protected Integer naturalHashCode() {
        if (name == null)
            return System.identityHashCode(this);
        else
            return name.hashCode();
    }

}
