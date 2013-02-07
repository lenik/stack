package com.bee32.ape.engine.identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.GeneratedByCopyPaste;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.UserQueryUtils;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.ape.engine.identity.composite.CompositeUserEntityManager;
import com.bee32.ape.engine.identity.icsf.IIcsfTypeMapping;
import com.bee32.ape.engine.identity.icsf.IcsfUserEntityManager;
import com.bee32.ape.engine.identity.mem.InMemoryUserEntityManager;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeUserEntityManager
        extends CompositeUserEntityManager {

    static final Logger logger = LoggerFactory.getLogger(ApeUserEntityManager.class);

    MemoryActivitiDatabase memdb;
    InMemoryUserEntityManager inMemManager;;
    IcsfUserEntityManager icsfManager;

    public ApeUserEntityManager(IIcsfTypeMapping icsfTypeMapping, MemoryActivitiDatabase memdb) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        if (memdb == null)
            throw new NullPointerException("memdb");

        this.memdb = memdb;

        addImplementation(inMemManager = new InMemoryUserEntityManager(memdb), false);
        addImplementation(icsfManager = new IcsfUserEntityManager(icsfTypeMapping), true);
    }

    @Override
    public void insertUser(User user) {
        logger.info("insertUser: " + user.getId());
        super.insertUser(user);
    }

    @Override
    public void updateUser(UserEntity updatedUser) {
        logger.info("updateUser: " + updatedUser.getId());
        super.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("deleteUser: " + userId);
        super.deleteUser(userId);
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        logger.info("checkPassword: " + userId + " with [" + password + "]");
        return super.checkPassword(userId, password);
    }

    @Override
    public UserEntity findUserById(String userId) {
        logger.info("findUserById: " + userId);
        return super.findUserById(userId);
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        logger.info("findUserCountByQueryCriteria");
        long count = super.findUserCountByQueryCriteria(query);
        logger.info("    count = " + count);
        return count;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        logger.info("findUserByQueryCriteria: page = " + page);
        logger.info("    query: " + UserQueryUtils.format(query));
        List<User> users = super.findUserByQueryCriteria(query, page);
        logger.info("    Found: " + UserEntityUtils.toString(users));
        return users;
    }

    @GeneratedByCopyPaste(value = ApeGroupEntityManager.class, method = "findGroupsByUser(String)")
    @Override
    public List<Group> findGroupsByUser(String userId) {
        logger.info("findGroupsByUser: " + userId);
        List<Group> groups = super.findGroupsByUser(userId);

        groups = normalize(groups);

        if ("admin".equals(userId))
            addImplicitGroup(groups, memdb.ADMIN_GROUP);
        if (true)
            addImplicitGroup(groups, memdb.USER_GROUP);

        logger.info("    Found: " + GroupEntityUtils.toString(groups));
        return groups;
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        logger.info("findUserInfoByUserIdAndKey: user = " + userId + ", key = " + key);
        return super.findUserInfoByUserIdAndKey(userId, key);
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        logger.info("findUserInfoKeysByUserIdAndType: user = " + userId + ", type = " + type);
        List<String> keys = super.findUserInfoKeysByUserIdAndType(userId, type);
        logger.info("    Found: " + keys);
        return keys;
    }

    @Override
    public List<User> findPotentialStarterUsers(String procDefId) {
        logger.info("findPotentialStarterUsers: processDefId = " + procDefId);
        List<User> users = super.findPotentialStarterUsers(procDefId);
        logger.info("    Found: " + UserEntityUtils.toString(users));
        return users;
    }

    Group normalize(Group group) {
        if ("admin".equals(group.getId())) {
            // Normalize admin group to ape:admin.
            return memdb.ADMIN_GROUP;
        }
        return group;
    }

    List<Group> normalize(Collection<? extends Group> groups) {
        List<Group> normalizedGroups = new ArrayList<Group>(groups.size());
        for (Group group : groups) {
            group = normalize(group);
            normalizedGroups.add(group);
        }
        return normalizedGroups;
    }

    void addImplicitGroup(List<Group> groups, Group group) {
        if (!groups.contains(group))
            groups.add(group);
    }

}
