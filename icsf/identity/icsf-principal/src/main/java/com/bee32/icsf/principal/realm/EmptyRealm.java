package com.bee32.icsf.principal.realm;

import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;

public class EmptyRealm
        extends AbstractRealm {

    private static final long serialVersionUID = 1L;

    public EmptyRealm() {
        super();
    }

    public EmptyRealm(String name) {
        super(name);
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public Collection<IPrincipal> getPrincipals() {
        return Collections.emptyList();
    }

    @Override
    public Collection<IUserPrincipal> getUsers() {
        return Collections.emptyList();
    }

    @Override
    public Collection<IGroupPrincipal> getGroups() {
        return Collections.emptyList();
    }

    @Override
    public Collection<IRolePrincipal> getRoles() {
        return Collections.emptyList();
    }

}
