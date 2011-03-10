package com.bee32.icsf.principal.realm;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.Component;

public class RealmBean
        extends AbstractRealm {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    protected Collection<IPrincipal> principals;

    @Override
    public Integer getPrimaryKey() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Collection<IPrincipal> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<IPrincipal> principals) {
        this.principals = principals;
    }

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = ((principals == null) ? 0 : principals.hashCode());
        return result;
    }

    @Override
    public boolean equalsSpecific(Component obj) {
        RealmBean other = (RealmBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(principals, other.principals))
            return false;

        return true;
    }

}
