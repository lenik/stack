package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "steoro", length = 3)
public class Principal
        extends EntityBean<Long>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    private String fullName;
    private String description;

    public static final char EMAIL_INIT = '?';
    public static final char EMAIL_VERIFYING = '1';
    public static final char EMAIL_VERIFIED = 'V';

    protected String email;
    protected char emailStatus = EMAIL_INIT;

    private Collection<Principal> impliedPrincipals;

    public Principal() {
        super();
    }

    public Principal(String name) {
        super(name);
    }

    @Basic(optional = false)
    @Column(length = 50, unique = true)
    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
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
        if (fullName != null)
            return fullName;
        return name;
    }

    @Column(length = 200)
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    public char getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(char emailStatus) {
        this.emailStatus = emailStatus;
    }

    @Transient
    public boolean isEmailVerified() {
        return emailStatus == EMAIL_VERIFIED;
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
    public boolean implies(IPrincipal principal) {
        if (impliedPrincipals == null)
            return false;

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
//
// @Override
// public void toString(PrettyPrintStream out, EntityFormat format) {
// String principalType = getClass().getSimpleName();
// String qname = principalType + " :: " + getName();
// out.print(qname);
// }

}
