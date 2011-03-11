package com.bee32.icsf.principal.realm;

import java.util.Collection;
import java.util.HashSet;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;

public class RealmBean
        extends AbstractRealm {

    private static final long serialVersionUID = 1L;

    protected Collection<IPrincipal> principals;
    protected Collection<IUserPrincipal> users;
    protected Collection<IGroupPrincipal> groups;
    protected Collection<IRolePrincipal> roles;

    @Override
    public Collection<IPrincipal> getPrincipals() {
        if (principals == null) {
            synchronized (this) {
                if (principals == null) {
                    principals = new HashSet<IPrincipal>();
                }
            }
        }
        return principals;
    }

    public void setPrincipals(Collection<IPrincipal> principals) {
        this.principals = principals;
    }

    @Override
    public Collection<IUserPrincipal> getUsers() {
        if (users == null) {
            synchronized (this) {
                if (users == null) {
                    users = new HashSet<IUserPrincipal>();
                }
            }
        }
        return users;
    }

    @Override
    public Collection<IGroupPrincipal> getGroups() {
        if (groups == null) {
            synchronized (this) {
                if (groups == null) {
                    groups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return groups;
    }

    @Override
    public Collection<IRolePrincipal> getRoles() {
        if (roles == null) {
            synchronized (this) {
                if (roles == null) {
                    roles = new HashSet<IRolePrincipal>();
                }
            }
        }
        return roles;
    }

}
