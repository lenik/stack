package com.bee32.icsf.principal;

import javax.inject.Inject;

public class UserDao
        extends AbstractPrincipalDao<User> {

    @Inject
    GroupDao groupDao;

    @Inject
    RoleDao roleDao;

    public boolean assignGroup(int userId, int groupId) {
        User user = getOrFail(userId);
        if (user == null)
            return false;

        Group group = groupDao.getOrFail(groupId);
        if (group == null)
            return false;

        user.addAssignedGroup(group);
        group.addMemberUser(user);
        update(user);
        return true;
    }

    public boolean unassignGroup(int userId, int groupId) {
        User user = getOrFail(userId);
        if (user == null)
            return false;

        Group group = groupDao.getOrFail(groupId);
        if (group == null)
            return false;

        user.removeAssignedGroup(group);
        group.removeMemberUser(user);
        update(user);
        return true;
    }

    public boolean assignGroupByName(String userName, String groupName) {
        if (userName == null)
            throw new NullPointerException("userName");
        if (groupName == null)
            throw new NullPointerException("groupName");

        User user = getByName(userName);
        if (user == null)
            return false;

        Group group = groupDao.getByName(groupName);
        if (group == null)
            return false;

        user.addAssignedGroup(group);
        group.addMemberUser(user);
        update(user);
        return true;
    }

    public boolean unassignGroupByName(String userName, String groupName) {
        if (userName == null)
            throw new NullPointerException("userName");
        if (groupName == null)
            throw new NullPointerException("groupName");

        User user = getByName(userName);
        if (user == null)
            return false;

        Group group = groupDao.getByName(groupName);
        if (group == null)
            return false;

        user.removeAssignedGroup(group);
        group.removeMemberUser(user);
        update(user);
        return true;
    }

    public boolean assignRole(int userId, int roleId) {
        User user = getOrFail(userId);
        if (user == null)
            return false;

        Role role = roleDao.getOrFail(roleId);
        if (role == null)
            return false;

        user.addAssignedRole(role);
        role.addResponsibleUser(user);
        update(user);
        return true;
    }

    public boolean unassignRole(int userId, int roleId) {
        User user = getOrFail(userId);
        if (user == null)
            return false;

        Role role = roleDao.getOrFail(roleId);
        if (role == null)
            return false;

        user.removeAssignedRole(role);
        role.removeResponsibleUser(user);
        update(user);
        return true;
    }

    public boolean assignRoleByName(String userName, String roleName) {
        if (userName == null)
            throw new NullPointerException("userName");
        if (roleName == null)
            throw new NullPointerException("roleName");

        User user = getByName(userName);
        if (user == null)
            return false;

        Role role = roleDao.getByName(roleName);
        if (role == null)
            return false;

        user.addAssignedRole(role);
        role.addResponsibleUser(user);
        update(user);
        return true;
    }

    public boolean unassignRoleByName(String userName, String roleName) {
        if (userName == null)
            throw new NullPointerException("userName");
        if (roleName == null)
            throw new NullPointerException("roleName");

        User user = getByName(userName);
        if (user == null)
            return false;

        Role role = roleDao.getByName(roleName);
        if (role == null)
            return false;

        user.removeAssignedRole(role);
        role.removeResponsibleUser(user);
        update(user);
        return true;
    }

}
