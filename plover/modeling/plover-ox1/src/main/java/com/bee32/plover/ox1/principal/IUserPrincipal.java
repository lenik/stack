package com.bee32.plover.ox1.principal;

import java.util.List;

public interface IUserPrincipal
        extends IPrincipal {

    /**
     * The primary role which this user stands for.
     *
     * @return The primary role assigned. <code>null</code> if none.
     */
    Role getPrimaryRole();

    /**
     * The primary group which this user stands for.
     *
     * @return The primary group assigned. <code>null</code> if none.
     */
    Group getPrimaryGroup();

    /**
     * 授予该用户的角色集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @return 如果没有被授予任何角色，返回一个不是 <code>null</code> 的空集。
     */
    List<? extends Role> getAssignedRoles();

    boolean addAssignedRole(Role role);

    boolean removeAssignedRole(Role role);

    /**
     * 授予该用户的组集。
     * <p>
     * <font color='red'>注意防止形成引用回路。 </font>
     *
     * @return 如果没有被授予任何组，返回一个不是 <code>null</code> 的空集。
     */
    List<? extends Group> getAssignedGroups();

    boolean addAssignedGroup(Group group);

    boolean removeAssignedGroup(Group group);

}
