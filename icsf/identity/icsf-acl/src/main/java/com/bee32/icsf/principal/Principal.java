package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "steoro", length = 3)
public class Principal
        extends GreenEntity<Long>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    public static final int NAME_MAXLEN = 16;

    boolean nameSet;
    String fullName;
    UserEmail email;

    Collection<Principal> impliedPrincipals;

    public Principal() {
        super();
    }

    public Principal(String name) {
        super(name);
    }

    @NaturalId
    @Basic(optional = false)
    @Column(length = NAME_MAXLEN, unique = true)
    @Override
    public String getName() {
        return super.getName();
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

        if (nameSet && !this.name.equals(name))
            throw new IllegalStateException("Principal.name is not mutable.");

        this.name = name;
        nameSet = true;
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
        if (fullName != null)
            return fullName;
        return name;
    }

    @ManyToOne
    @Cascade(CascadeType.ALL)
    public UserEmail getEmail() {
        return email;
    }

    public void setEmail(UserEmail email) {
        this.email = email;
    }

    @Transient
    public String getEmailAddress() {
        if (email == null)
            return null;
        else
            return email.getAddress();
    }

    public void setEmailAddress(String emailAddress) {
        if (email == null)
            email = new UserEmail(this, emailAddress);
        else
            email.setAddress(emailAddress);
    }

    @ManyToMany(targetEntity = Principal.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "PrincipalImp", //
    /*            */joinColumns = @JoinColumn(name = "imp"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "principal"))
    public Collection<Principal> getImpliedPrincipals() {
        if (impliedPrincipals == null) {
            synchronized (this) {
                if (impliedPrincipals == null) {
                    impliedPrincipals = new HashSet<Principal>();
                }
            }
        }
        return impliedPrincipals;
    }

    public void setImpliedPrincipals(Collection<? extends Principal> impliedPrincipals) {
        this.impliedPrincipals = new HashSet<Principal>(impliedPrincipals);
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

        if (impliedPrincipals != null)
            for (IPrincipal impliedPrincipal : impliedPrincipals)
                if (impliedPrincipal.implies(principal))
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
    protected Boolean naturalEquals(EntityBase<Long> other) {
        Principal o = (Principal) other;

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
