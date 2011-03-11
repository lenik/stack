package com.bee32.icsf.principal;

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
     * 获取组的拥有者。
     * <p>
     * 这个拥有者只是为了方便进行类的设计，与实际应用的拥有者可能无关。
     * <p>
     * 组拥有者的具体解释，比如是否有权管理组等，不在本类中解释。
     * <p>
     * 组可能有更复杂的管理结构，应该在其它关系中定义那些更复杂的结构。
     *
     * @return 如果没有设置拥有者，返回空。
     */
    IUserPrincipal getOwner();

    void setOwner(IUserPrincipal owner);

    /**
     * 获取组的主要角色。
     *
     * @return 如果没有设置主要角色，返回空。
     */
    IRolePrincipal getPrimaryRole();

    void setPrimaryRole(IRolePrincipal role);

    /**
     * 授予该组的角色集。
     * <p>
     * <font color='red'>注意形成引用回路。 </font>
     *
     * @see PrincipalDiag#checkDeadLoop(IPrincipal)
     *
     * @return 如果没有被授予任何角色，返回空集。
     */
    Collection<IRolePrincipal> getAssignedRoles();

    void addAssignedRole(IRolePrincipal role);

    void removeAssignedRole(IRolePrincipal role);

    /**
     * 获取成员用户。
     *
     * @return 如果没有成员，返回空集。
     */
    Collection<IUserPrincipal> getMemberUsers();

    void addMemberUser(IUserPrincipal user);

    void removeMemberUser(IUserPrincipal user);

}
