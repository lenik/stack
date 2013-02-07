package com.bee32.ape.engine.identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.GroupQueryUtils;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.ape.engine.identity.composite.CompositeGroupEntityManager;
import com.bee32.ape.engine.identity.icsf.IIcsfTypeMapping;
import com.bee32.ape.engine.identity.icsf.IcsfGroupEntityManager;
import com.bee32.ape.engine.identity.mem.InMemoryGroupEntityManager;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeGroupEntityManager
        extends CompositeGroupEntityManager {

    static final Logger logger = LoggerFactory.getLogger(ApeGroupEntityManager.class);

    MemoryActivitiDatabase memdb;
    InMemoryGroupEntityManager inMemManager;;
    IcsfGroupEntityManager icsfManager;

    public ApeGroupEntityManager(IIcsfTypeMapping icsfTypeMapping, MemoryActivitiDatabase memdb) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        if (memdb == null)
            throw new NullPointerException("memdb");

        this.memdb = memdb;

        addImplementation(inMemManager = new InMemoryGroupEntityManager(memdb), false);
        addImplementation(icsfManager = new IcsfGroupEntityManager(icsfTypeMapping), true);
    }

    @Override
    public void insertGroup(Group group) {
        logger.info("insertGroup: " + group.getId());
        super.insertGroup(group);
    }

    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        logger.info("updateGroup: " + updatedGroup.getId());
        super.updateGroup(updatedGroup);
    }

    @Override
    public void deleteGroup(String groupId) {
        logger.info("deleteGroup: " + groupId);
        super.deleteGroup(groupId);
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        logger.info("findGroupById: " + groupId);
        return super.findGroupById(groupId);
    }

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
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        logger.info("findGroupCountByQueryCriteria");

        long count = super.findGroupCountByQueryCriteria(query);

        // if (GroupEntityUtils.match(memdb.ADMIN_GROUP, query))
        // count++;
        if (GroupEntityUtils.match(memdb.USER_GROUP, query))
            count++;

        return count;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        logger.info("findGroupByQueryCriteria: page=" + page);
        logger.info("    query: " + GroupQueryUtils.format(query));

        List<Group> groups = super.findGroupByQueryCriteria(query, page);

        groups = normalize(groups);

        if (GroupEntityUtils.match(memdb.ADMIN_GROUP, query))
            addImplicitGroup(groups, memdb.ADMIN_GROUP);
        if (GroupEntityUtils.match(memdb.USER_GROUP, query))
            addImplicitGroup(groups, memdb.USER_GROUP);

        logger.info("    found: " + GroupEntityUtils.toString(groups));
        return groups;
    }

    Group normalize(Group group) {
        if ("admin".equals(group.getId())) {
            return memdb.ADMIN_GROUP;
        }
        if ("user".equals(group.getId())) {
            return memdb.USER_GROUP;
        }
        return group;
    }

    List<Group> normalize(Collection<? extends Group> groups) {
        List<Group> normalizedGroups = new ArrayList<Group>(groups.size());
        for (Group group : groups) {
            group = normalize(group);
            if (!normalizedGroups.contains(group))
                normalizedGroups.add(group);
        }
        return normalizedGroups;
    }

    void addImplicitGroup(List<Group> groups, Group group) {
        if (!groups.contains(group))
            groups.add(group);
    }

}
