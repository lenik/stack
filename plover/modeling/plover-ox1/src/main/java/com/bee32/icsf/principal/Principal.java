package com.bee32.icsf.principal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 安全主体
 *
 * 用于访问安全控制的主体对象。
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "principal_seq", allocationSize = 1)
public abstract class Principal
        extends TreeEntityAuto<Integer, Principal>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 16;

    String name;

    public Principal() {
        this.name = null;
    }

    public Principal(String name) {
        setName(name);
    }

    public Principal(String name, String fullName) {
        setName(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Principal)
            _populate((Principal) source);
        else
            super.populate(source);
    }

    protected void _populate(Principal o) {
        super._populate(o);
        name = o.name;
    }

    @Transient
    @Override
    protected boolean isUniqueChildren() {
        return true;
    }

    /**
     * 主体名称
     *
     * 系统中唯一的主体名称（用户名、组名等）
     */
    @NaturalId(mutable = true)
    @Column(length = NAME_LENGTH, nullable = false)
    @Override
    public String getName() {
        return name;
    }

    /**
     * You should not change the name of a principal.
     * <p>
     * Names are automaticlly converted to lower-case.
     *
     * @param name
     *            Non-<code>null</code> name to set.
     * @see #NAME_LENGTH
     */
    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");

        name = name.toLowerCase(Locale.ROOT);

        if (this.name != null && !this.name.equals(name))
            throw new IllegalStateException("Principal.name is not mutable: " + this.name + " -> " + name);

        this.name = name;
    }

    @Transient
    public String getFullName() {
        return getLabel();
    }

    public void setFullName(String fullName) {
        setLabel(fullName);
    }

    public void setFullName(String firstName, String lastName) {
        String fullName = (firstName + " " + lastName).trim();
        setFullName(fullName);
    }

    @Transient
    public String getDisplayName() {
        if (!StringUtils.isEmpty(getLabel()))
            return getLabel();
        else
            return name;
    }

    @Override
    public Principal detach() {
        super.detach();
        return this;
    }

    protected void invalidateRelations() {
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

    @Transient
    public final Set<Principal> getImSet() {
        Set<Principal> imSet = new HashSet<Principal>();
        populateImSet(imSet);
        return imSet;
    }

    @Transient
    public final Set<Principal> getInvSet() {
        Set<Principal> invSet = new HashSet<Principal>();
        populateInvSet(invSet);
        return invSet;
    }

    protected void populateImSet(Set<Principal> imSet) {
        imSet.add(this);
    }

    protected void populateInvSet(Set<Principal> invSet) {
        invSet.add(this);
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            throw new NullPointerException("name");
        return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

    @Override
    protected void formatEntryText(StringBuilder buf) {
        buf.append(getDisplayName());
    }

}
