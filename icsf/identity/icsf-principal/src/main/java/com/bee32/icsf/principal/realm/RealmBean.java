package com.bee32.icsf.principal.realm;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.IEntity;

public class RealmBean
        extends AbstractRealm
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    protected Collection<IPrincipal> principals;

    @Override
    public Integer getPrimaryKey() {
        return id;
    }

    @Override
    public Collection<IPrincipal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<IPrincipal> principals) {
        this.principals = principals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = super.hashCode();
        result = prime * result + ((principals == null) ? 0 : principals.hashCode());

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
        RealmBean other = (RealmBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(principals, other.principals))
            return false;

        return true;
    }

}
