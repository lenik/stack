package com.bee32.icsf.principal.realm;

import java.util.Collection;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.IComponent;

public interface IRealm
        extends IComponent {

    boolean contains(IPrincipal principal);

    Collection<? extends IPrincipal> getPrincipals();

    Collection<? extends IUserPrincipal> getUsers();

    Collection<? extends IGroupPrincipal> getGroups();

    Collection<? extends IRolePrincipal> getRoles();

}
