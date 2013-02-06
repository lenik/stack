package com.bee32.ape.engine.identity.mem;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

public class MemoryActivitiDatabase {

    private Map<String, UserEntity> userMap = new HashMap<>();
    private Map<String, GroupEntity> groupMap = new HashMap<>();

    private Map<String, Set<String>> userGroups = new HashMap<>();
    private Map<String, Set<String>> groupUsers = new HashMap<>();

    public UserEntity getUser(String userId) {
        return userMap.get(userId);
    }

    public GroupEntity getGroup(String groupId) {
        return groupMap.get(groupId);
    }

    public Collection<UserEntity> getUsers() {
        return userMap.values();
    }

    public Collection<GroupEntity> getGroups() {
        return groupMap.values();
    }

    public synchronized void addUser(UserEntity user) {
        if (user == null)
            throw new NullPointerException("user");

        String userId = user.getId();
        if (userId == null)
            throw new NullPointerException("userId");

        UserEntity existing = userMap.get(userId);
        if (existing != null)
            throw new IllegalStateException("User is already existed: " + userId);

        userMap.put(userId, user);
    }

    public synchronized void addGroup(GroupEntity group) {
        if (group == null)
            throw new NullPointerException("group");

        String groupId = group.getId();
        if (groupId == null)
            throw new NullPointerException("groupId");

        GroupEntity existing = groupMap.get(groupId);
        if (existing != null)
            throw new IllegalStateException("Group is already existed: " + groupId);

        groupMap.put(groupId, group);
    }

    public synchronized void removeUserById(String userId) {
        if (userId == null)
            throw new NullPointerException("userId");
        userMap.remove(userId);
        userGroups.remove(userId);
        for (Set<String> set : groupUsers.values())
            set.remove(userId);
    }

    public synchronized void removeGroupById(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        groupMap.remove(groupId);
        groupUsers.remove(groupId);
        for (Set<String> set : userGroups.values())
            set.remove(groupId);
    }

    public synchronized Set<String> getGroupIdSet(String userId) {
        if (userId == null)
            throw new NullPointerException("userId");
        Set<String> groups = userGroups.get(userId);
        if (groups == null)
            userGroups.put(userId, groups = new HashSet<>());
        return groups;
    }

    public synchronized Set<String> getMemberIdSet(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        Set<String> members = groupUsers.get(groupId);
        if (members == null)
            groupUsers.put(groupId, members = new HashSet<>());
        return members;
    }

    public synchronized void addMembership(Group group, User user) {
        if (group == null)
            throw new NullPointerException("group");
        if (user == null)
            throw new NullPointerException("user");
        addMembership(group.getId(), user.getId());
    }

    public synchronized void addMembership(String groupId, String userId) {
        if (userId == null)
            throw new NullPointerException("userId");
        if (groupId == null)
            throw new NullPointerException("groupId");
        getGroupIdSet(userId).add(groupId);
        getMemberIdSet(groupId).add(userId);
    }

    public GroupEntity ADMIN_GROUP = new GroupEntity("admin");
    public GroupEntity GUEST_GROUP = new GroupEntity("guest");
    public UserEntity ADMIN_USER = new UserEntity("admin");
    public UserEntity GUEST_USER = new UserEntity("guest");
    {
        addGroup(ADMIN_GROUP);
        addGroup(GUEST_GROUP);
        addUser(ADMIN_USER);
        addUser(GUEST_USER);
        addMembership(ADMIN_GROUP, ADMIN_USER);
        addMembership(GUEST_GROUP, GUEST_USER);
    }

}
