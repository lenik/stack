package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.tree.TreeEntity;

@Entity
@Green
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "steoro", length = 3)
public abstract class Principal<$ extends Principal<$>>
        extends TreeEntity<String, $>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    public static final int NAME_MAXLEN = 16;

    protected final Class<$> echo = (Class<$>) getClass();

    String name;
    String fullName;

    public Principal() {
        this.name = null;
    }

    public Principal(String name) {
        setName(name);
    }

    @Id
    @Basic(optional = false)
    @Column(length = NAME_MAXLEN, unique = true)
    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @Transient
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
    protected void setName(String name) {
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
    public $ detach() {
        super.detach();
        return echo.cast(this);
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
    protected Boolean naturalEquals(EntityBase<String> other) {
        $ o = echo.cast(other);

        if (this.name == null || o.name == null)
            return false;

        return name.equals(o.name);
    }

    @Override
    protected Integer naturalHashCode() {
        if (name == null)
            return 0;
        else
            return name.hashCode();
    }

}
