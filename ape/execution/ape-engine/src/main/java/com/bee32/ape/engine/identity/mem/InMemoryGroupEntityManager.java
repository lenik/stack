package com.bee32.ape.engine.identity.mem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.free.ReverseComparator;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.GroupQueryProperty;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import com.bee32.ape.engine.identity.ActivitiOrderUtils;
import com.bee32.ape.engine.identity.GroupEntityUtils;

public class InMemoryGroupEntityManager
        extends GroupEntityManager {

    MemoryActivitiDatabase db;

    public InMemoryGroupEntityManager(MemoryActivitiDatabase db) {
        if (db == null)
            throw new NullPointerException("db");
        this.db = db;
    }

    @Override
    public void insertGroup(Group _group) {
        if (_group == null)
            throw new NullPointerException("_group");

        GroupEntity group = (GroupEntity) _group;
        db.addGroup(group);
    }

    @Override
    public void updateGroup(GroupEntity updatedGroup) {

        GroupEntity existing = findGroupById(updatedGroup.getId());
        if (existing == null)
            throw new ActivitiException("Group isn't existed: " + updatedGroup.getId());

        if (existing != updatedGroup)
            GroupEntityUtils.populate(existing, updatedGroup);
    }

    @Override
    public void deleteGroup(String groupId) {
        db.removeGroupById(groupId);
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        long count = 0;
        for (GroupEntity group : db.getGroups())
            if (match(group, query))
                count++;
        return count;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        int offset = page == null ? 0 : page.getFirstResult();
        int max = page == null ? -1 : page.getMaxResults();
        if (max == -1)
            max = Integer.MAX_VALUE;

        List<Group> list = new ArrayList<>();
        int index = 0;

        for (GroupEntity group : db.getGroups()) {
            if (match(group, query))
                if (index >= offset)
                    list.add(group);

            index++;
            if (list.size() >= max)
                break;
        }

        if (query.getOrderBy() != null) {
            Comparator<Group> comparator = GroupIdComparator.getInstance();
            GroupQueryProperty orderProperty = GroupEntityUtils.getOrderProperty(query.getOrderBy());
            if (orderProperty == GroupQueryProperty.GROUP_ID)
                comparator = GroupIdComparator.getInstance();
            else if (orderProperty == GroupQueryProperty.NAME)
                comparator = GroupNameComparator.getInstance();
            else if (orderProperty == GroupQueryProperty.TYPE)
                comparator = GroupTypeComparator.getInstance();

            if (!ActivitiOrderUtils.isAscending(query.getOrderBy()))
                comparator = new ReverseComparator<Group>(comparator);

            Collections.sort(list, comparator);
        }

        return list;
    }

    boolean match(GroupEntity group, GroupQueryImpl query) {
        if (!GroupEntityUtils.match(group, query))
            return false;

        String userId = query.getUserId();
        if (userId != null) {
            Set<String> members = db.getMemberIdSet(group.getId());
            if (!members.contains(userId))
                return false;
        }

        // String proceDefId = query.getProceDefId();
        return true;
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        return db.getGroup(groupId);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Group> groups = new ArrayList<>();
        for (String groupId : db.getGroupIdSet(userId))
            groups.add(db.getGroup(groupId));
        return groups;
    }

    @Override
    public List<Group> findPotentialStarterUsers(String proceDefId) {
        List<Group> starterGroups = new ArrayList<>();
        starterGroups.addAll(db.getGroups());
        return starterGroups;
    }

}
