package com.bee32.plover.ox1.principal;

import java.util.List;

public interface IRolePrincipal
        extends IPrincipal {

    IRolePrincipal getInheritedRole();

    List<? extends IRolePrincipal> getDerivedRoles();

    List<? extends IUserPrincipal> getResponsibleUsers();

    List<? extends IGroupPrincipal> getResponsibleGroups();

    boolean addResponsibleUser(User user);

    boolean removeResponsibleUser(User user);

    boolean addResponsibleGroup(Group group);

    boolean removeResponsibleGroup(Group group);

}
