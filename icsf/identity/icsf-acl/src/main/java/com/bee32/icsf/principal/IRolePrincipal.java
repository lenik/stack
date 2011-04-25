package com.bee32.icsf.principal;

import java.util.Set;

public interface IRolePrincipal
        extends IPrincipal {

    /**
     * 获取继承的角色。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有继承任何角色，返回 <code>null</code>。
     */
    IRolePrincipal getInheritedRole();

    Set<? extends IRolePrincipal> getDerivedRoles();

    Set<? extends IUserPrincipal> getResponsibleUsers();

    Set<? extends IGroupPrincipal> getResponsibleGroups();

    boolean addResponsibleUser(IUserPrincipal user);

    boolean removeResponsibleUser(IUserPrincipal user);

    boolean addResponsibleGroup(IGroupPrincipal group);

    boolean removeResponsibleGroup(IGroupPrincipal group);

}
