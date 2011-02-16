package com.bee32.sems.security.access;

import java.util.Collection;

public interface IGroupPrincipal
        extends IPrincipal {

    /**
     * 获取继承的组。
     * <p>
     * <font color='red'>注意形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有继承任何组，返回 <code>null</code>。
     */
    IGroupPrincipal getInheritedGroup();

    /**
     * 授予该组的角色集。
     * <p>
     * <font color='red'>注意形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何角色，返回空集。
     */
    Collection<? extends IRolePrincipal> getAssignedRoles();

    /**
     * 获取成员用户。
     *
     * @return 如果没有成员，返回空集。
     */
    Collection<? extends IUserPrincipal> getMemberUsers();

}
