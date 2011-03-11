package com.bee32.icsf.principal.realm;

import java.util.Collection;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.Component;

public abstract class AbstractRealm
        extends Component
        implements IRealm {

    private static final long serialVersionUID = 1L;

    private Integer id;

    public AbstractRealm() {
        super();
    }

    public AbstractRealm(String name) {
        super(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean contains(IPrincipal principal) {
        Collection<IPrincipal> principals = getPrincipals();

        if (principals == null)
            return false;

        return principals.contains(principal);
    }

    @Override
    public void addPrincipal(IPrincipal principal) {
        getPrincipals().add(principal);
    }

    @Override
    public void removePrincipal(IPrincipal principal) {
        getPrincipals().remove(principal);
    }

    @Override
    public void addUser(IUserPrincipal user) {
        getUsers().add(user);
    }

    @Override
    public void removeUser(IUserPrincipal user) {
        getUsers().remove(user);
    }

    @Override
    public void addGroup(IGroupPrincipal group) {
        getGroups().add(group);
    }

    @Override
    public void removeGroup(IGroupPrincipal group) {
        getGroups().remove(group);
    }

    @Override
    public void addRole(IRolePrincipal role) {
        getRoles().add(role);
    }

    @Override
    public void removeRole(IRolePrincipal role) {
        getRoles().remove(role);
    }

}
