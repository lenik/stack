package com.bee32.icsf.principal;

import java.util.List;

public interface IRolePrincipal
        extends IPrincipal {

    IRolePrincipal getInheritedRole();

    List<? extends IRolePrincipal> getDerivedRoles();

    List<? extends IUserPrincipal> getControlUsers();

    List<? extends IGroupPrincipal> getControlGroups();

    List<? extends IUserPrincipal> getResponsibleUsers();

    List<? extends IGroupPrincipal> getResponsibleGroups();

    boolean addControlUser(User user);

    boolean removeControlUser(User user);

    boolean addControlGroup(Group group);

    boolean removeControlGroup(Group group);

    boolean addResponsibleUser(User user);

    boolean removeResponsibleUser(User user);

    boolean addResponsibleGroup(Group group);

    boolean removeResponsibleGroup(Group group);

}
