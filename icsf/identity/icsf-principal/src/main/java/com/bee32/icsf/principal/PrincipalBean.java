package com.bee32.icsf.principal;

import java.util.Collection;

import javax.persistence.Transient;

import com.bee32.plover.orm.entity.IEntity;

public class PrincipalBean
        extends AbstractPrincipal
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Collection<IPrincipal> impliedPrincipals;

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    // @ManyToMany(cascade = CascadeType.ALL, mappedBy = "impl", targetEntity = PrincipalBean.class)
    @Transient
    public Collection<IPrincipal> getImpliedPrincipals() {
        return impliedPrincipals;
    }

    public void setImpliedPrincipals(Collection<IPrincipal> impliedPrincipals) {
        this.impliedPrincipals = impliedPrincipals;
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
    public void accept(IPrincipalVisitor visitor) {
    }

}
