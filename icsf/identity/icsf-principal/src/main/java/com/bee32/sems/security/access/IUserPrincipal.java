package com.bee32.sems.security.access;

import java.util.Collection;

public interface IUserPrincipal
        extends IPrincipal {

    /**
     * 授予该用户的角色集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何角色，返回一个不是 <code>null</code> 的空集。
     */
    Collection<? extends IRolePrincipal> getAssignedRoles();

    /**
     * 授予该用户的组集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何组，返回一个不是 <code>null</code> 的空集。
     */
    Collection<? extends IGroupPrincipal> getAssignedGroups();

}
