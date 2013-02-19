package com.bee32.icsf.principal;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;

public class UserService
        extends DataService {

    @Inject
    UserDao userDao;

    @Transactional
    public boolean assignGroup(int userId, int groupId) {
        return userDao.assignGroup(userId, groupId);
    }

    @Transactional
    public boolean unassignGroup(int userId, int groupId) {
        return userDao.unassignGroup(userId, groupId);
    }

    @Transactional
    public boolean assignGroupByName(String userName, String groupName) {
        return userDao.assignGroupByName(userName, groupName);
    }

    @Transactional
    public boolean unassignGroupByName(String userName, String groupName) {
        return userDao.unassignGroupByName(userName, groupName);
    }

    @Transactional
    public boolean assignRole(int userId, int roleId) {
        return userDao.assignRole(userId, roleId);
    }

    @Transactional
    public boolean unassignRole(int userId, int roleId) {
        return userDao.unassignRole(userId, roleId);
    }

    @Transactional
    public boolean assignRoleByName(String userName, String roleName) {
        return userDao.assignRoleByName(userName, roleName);
    }

    @Transactional
    public boolean unassignRoleByName(String userName, String roleName) {
        return userDao.unassignRoleByName(userName, roleName);
    }

}
