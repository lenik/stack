package com.bee32.icsf.principal.realm;

import java.util.Collection;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.IComponent;
import com.bee32.plover.orm.entity.IEntity;

public interface IRealm
        extends IComponent, IEntity<Integer> {

    boolean contains(IPrincipal principal);

    Collection<? extends IPrincipal> getPrincipals();

    Collection<? extends IUserPrincipal> getUsers();

    Collection<? extends IGroupPrincipal> getGroups();

    Collection<? extends IRolePrincipal> getRoles();

}
