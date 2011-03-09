package com.bee32.icsf.principal.realm;

import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.Component;

public abstract class AbstractRealm
        extends Component
        implements IRealm {

    private static final long serialVersionUID = 1L;

    public AbstractRealm() {
        super();
    }

    public AbstractRealm(String name) {
        super(name);
    }

    @Override
    public boolean contains(IPrincipal principal) {
        Collection<? extends IPrincipal> principals = getPrincipals();

        if (principals == null)
            return false;

        return principals.contains(principal);
    }

    @Override
    public Collection<? extends IPrincipal> getPrincipals() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends IUserPrincipal> getUsers() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends IGroupPrincipal> getGroups() {
        return Collections.emptyList();
    }

    @Override
    public Collection<? extends IRolePrincipal> getRoles() {
        return Collections.emptyList();
    }

}
