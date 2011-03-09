package com.bee32.icsf.principal;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.plover.orm.entity.IEntity;

public class PrincipalBean
        extends AbstractPrincipal
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected Collection<IPrincipal> impliedPrincipals;

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = super.hashCode();
        result = prime * result + ((impliedPrincipals == null) ? 0 : impliedPrincipals.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PrincipalBean other = (PrincipalBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(impliedPrincipals, other.impliedPrincipals))
            return false;

        return true;
    }

}
