package com.bee32.icsf.principal;

import java.util.Set;

public interface IUserPrincipal
        extends IPrincipal {

    /**
     * The primary role which this user stands for.
     *
     * @return The primary role assigned. <code>null</code> if none.
     */
    IRolePrincipal getPrimaryRole();

    /**
     * The primary group which this user stands for.
     *
     * @return The primary group assigned. <code>null</code> if none.
     */
    IGroupPrincipal getPrimaryGroup();

    /**
     * 授予该用户的角色集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何角色，返回一个不是 <code>null</code> 的空集。
     */
    Set<? extends IRolePrincipal> getAssignedRoles();

    void addAssignedRole(IRolePrincipal role);

    void removeAssignedRole(IRolePrincipal role);

    /**
     * 授予该用户的组集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何组，返回一个不是 <code>null</code> 的空集。
     */
    Set<? extends IGroupPrincipal> getAssignedGroups();

    void addAssignedGroup(IGroupPrincipal group);

    void removeAssignedGroup(IGroupPrincipal group);

}
