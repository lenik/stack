package com.bee32.icsf.principal;

import java.util.List;

public interface IGroupPrincipal
        extends IPrincipal {

    /**
     * 获取继承的组。
     * <p>
     * <font color='red'>注意形成引用回路。 </font>
     *
     * @return 如果没有继承任何组，返回 <code>null</code>。
     */
    Group getInheritedGroup();

    List<Group> getDerivedGroups();

    /**
     * 获取组的主要角色。
     *
     * @return 如果没有设置主要角色，返回空。
     */
    Role getPrimaryRole();

    void setPrimaryRole(Role role);

    /**
     * 授予该组的角色集。
     * <p>
     * <font color='red'>注意形成引用回路。 </font>
     *
     * @return 如果没有被授予任何角色，返回空集。
     */
    List<? extends Role> getAssignedRoles();

    boolean addAssignedRole(Role role);

    boolean removeAssignedRole(Role role);

    /**
     * 获取成员用户。
     *
     * @return 如果没有成员，返回空集。
     */
    List<? extends User> getMemberUsers();

    boolean addMemberUser(User user);

    boolean removeMemberUser(User user);

}
