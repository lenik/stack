package com.bee32.ape.engine.identity.composite;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeUserEntityManager
        extends UserEntityManager {

    static final Logger logger = LoggerFactory.getLogger(CompositeUserEntityManager.class);

    private List<UserEntityManager> implementations = new ArrayList<>();
    private UserEntityManager master;

    public CompositeUserEntityManager() {
    }

    public void addImplementation(UserEntityManager implementation, boolean master) {
        if (implementation == null)
            throw new NullPointerException("implementation");
        implementations.add(implementation);
        if (master)
            this.master = implementation;
    }

    public UserEntityManager getMaster() {
        if (master == null)
            throw new IllegalStateException("master implementation isn't set.");
        return master;
    }

    @Override
    public void insertUser(User user) {
        getMaster().insertUser(user);
    }

    @Override
    public void updateUser(UserEntity updatedUser) {
        getMaster().updateUser(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        for (UserEntityManager impl : implementations)
            impl.deleteUser(userId);
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        for (UserEntityManager impl : implementations) {
            Boolean result = impl.checkPassword(userId, password);
            if (result != null)
                if (result)
                    return true;
        }
        return false;
    }

    @Override
    public UserEntity findUserById(String userId) {
        for (UserEntityManager impl : implementations) {
            UserEntity user = impl.findUserById(userId);
            if (user != null)
                return user;
        }
        return null;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        long count = 0;
        for (UserEntityManager impl : implementations)
            count += impl.findUserCountByQueryCriteria(query);
        return count;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        Set<User> union = new LinkedHashSet<User>();

        if (page == null) {
            for (UserEntityManager impl : implementations) {
                List<User> list = impl.findUserByQueryCriteria(query, null);
                union.addAll(list);
            }
        } else {
            int firstResult = page.getFirstResult();
            int maxResults = page.getMaxResults();

            int beginIndex = 0;
            for (UserEntityManager impl : implementations) {

                int localOffset = firstResult - beginIndex;
                if (localOffset < 0)
                    localOffset = 0;

                int max = maxResults - union.size();
                if (max <= 0)
                    break;

                union.addAll(impl.findUserByQueryCriteria(query, new Page(localOffset, max)));
                beginIndex += impl.findUserCountByQueryCriteria(query);
            }
        }
        List<User> users = new ArrayList<User>(union);
        return users;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        Set<Group> union = new LinkedHashSet<>();
        for (UserEntityManager impl : implementations) {
            List<Group> list = impl.findGroupsByUser(userId);
            union.addAll(list);
        }
        List<Group> groups = new ArrayList<Group>(union);
        return groups;
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        for (UserEntityManager impl : implementations) {
            IdentityInfoEntity found = impl.findUserInfoByUserIdAndKey(userId, key);
            if (found != null)
                return found;
        }
        return null;
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        Set<String> union = new LinkedHashSet<>();
        for (UserEntityManager impl : implementations) {
            List<String> list = impl.findUserInfoKeysByUserIdAndType(userId, type);
            union.addAll(list);
        }
        ArrayList<String> keys = new ArrayList<String>(union);
        return keys;
    }

    @Override
    public List<User> findPotentialStarterUsers(String procDefId) {
        Set<User> union = new LinkedHashSet<>();
        for (UserEntityManager impl : implementations) {
            List<User> list = impl.findPotentialStarterUsers(procDefId);
            union.addAll(list);
        }
        List<User> users = new ArrayList<User>(union);
        return users;
    }

}
