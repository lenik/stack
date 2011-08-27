package com.bee32.icsf.principal.realm;

import java.util.Set;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.ox1.principal.IGroupPrincipal;
import com.bee32.plover.ox1.principal.IPrincipal;
import com.bee32.plover.ox1.principal.IRolePrincipal;
import com.bee32.plover.ox1.principal.IUserPrincipal;

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
