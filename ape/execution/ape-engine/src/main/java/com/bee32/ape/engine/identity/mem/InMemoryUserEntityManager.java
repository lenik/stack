package com.bee32.ape.engine.identity.mem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.free.ReverseComparator;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import com.bee32.ape.engine.identity.ActivitiOrderUtils;
import com.bee32.ape.engine.identity.UserEntityUtils;

public class InMemoryUserEntityManager
        extends UserEntityManager {

    MemoryActivitiDatabase db;

    public InMemoryUserEntityManager(MemoryActivitiDatabase db) {
        if (db == null)
            throw new NullPointerException("db");
        this.db = db;
    }

    @Override
    public void insertUser(User _user) {
        if (_user == null)
            throw new NullPointerException("_user");

        UserEntity user = (UserEntity) _user;
        db.addUser(user);
    }

    @Override
    public void updateUser(UserEntity updatedUser) {
        UserEntity existing = findUserById(updatedUser.getId());
        if (existing == null)
            throw new ActivitiException("User isn't existed: " + updatedUser.getId());

        if (existing != updatedUser)
            UserEntityUtils.populate(existing, updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        db.removeUserById(userId);
    }

    @Override
    public UserEntity findUserById(String userId) {
        if (userId == null)
            throw new NullPointerException("userId");
        return db.getUser(userId);
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        UserEntity user = findUserById(userId);
        if (user == null)
            return false;

        String expected = user.getPassword();
        if (expected == null)
            return false;

        return expected.equals(password);
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        long count = 0;
        for (UserEntity user : db.getUsers())
            if (match(user, query))
                count++;
        return count;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        int offset = page == null ? 0 : page.getFirstResult();
        int max = page == null ? -1 : page.getMaxResults();
        if (max == -1)
            max = Integer.MAX_VALUE;

        List<User> list = new ArrayList<>();
        int index = 0;

        for (UserEntity user : db.getUsers()) {
            if (match(user, query))
                if (index >= offset)
                    list.add(user);

            index++;
            if (list.size() >= max)
                break;
        }

        if (query.getOrderBy() != null) {
            Comparator<User> comparator = UserIdComparator.getInstance();
            UserQueryProperty orderProperty = UserEntityUtils.getOrderProperty(query.getOrderBy());
            if (orderProperty == UserQueryProperty.USER_ID)
                comparator = UserIdComparator.getInstance();
            else if (orderProperty == UserQueryProperty.FIRST_NAME)
                comparator = UserFirstNameComparator.getInstance();
            else if (orderProperty == UserQueryProperty.LAST_NAME)
                comparator = UserLastNameComparator.getInstance();
            else if (orderProperty == UserQueryProperty.EMAIL)
                comparator = UserEmailComparator.getInstance();

            if (!ActivitiOrderUtils.isAscending(query.getOrderBy()))
                comparator = new ReverseComparator<User>(comparator);

            Collections.sort(list, comparator);
        }

        return list;
    }

    boolean match(UserEntity user, UserQueryImpl query) {
        if (!UserEntityUtils.match(user, query))
            return false;

        String groupId = query.getGroupId();
        if (groupId != null) {
            Set<String> groups = db.getGroupIdSet(user.getId());
            if (!groups.contains(groupId))
                return false;
        }

        // String proceDefId = query.getProceDefId();
        return true;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Group> groups = new ArrayList<>();
        for (String groupId : db.getGroupIdSet(userId))
            groups.add(db.getGroup(groupId));
        return groups;
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        return null;
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        List<String> keys = new ArrayList<>();
        return keys;
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        List<User> starterUsers = new ArrayList<>();
        starterUsers.addAll(db.getUsers());
        return starterUsers;
    }

}
