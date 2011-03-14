package com.bee32.icsf.principal.realm;

import java.util.Set;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.IComponent;
import com.bee32.plover.orm.entity.IEntity;

public interface IRealm
        extends IComponent, IEntity<Integer> {

    boolean contains(IPrincipal principal);

    Set<? extends IPrincipal> getPrincipals();

    Set<? extends IUserPrincipal> getUsers();

    Set<? extends IGroupPrincipal> getGroups();

    Set<? extends IRolePrincipal> getRoles();

    void addPrincipal(IPrincipal principal);

    void removePrincipal(IPrincipal principal);

    void addUser(IUserPrincipal user);

    void removeUser(IUserPrincipal user);

    void addGroup(IGroupPrincipal group);

    void removeGroup(IGroupPrincipal group);

    void addRole(IRolePrincipal role);

    void removeRole(IRolePrincipal role);

}
