package com.bee32.ape.engine.identity.composite;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeGroupEntityManager
        extends GroupEntityManager {

    static final Logger logger = LoggerFactory.getLogger(CompositeGroupEntityManager.class);

    private List<GroupEntityManager> implementations = new ArrayList<>();
    private GroupEntityManager master;

    public CompositeGroupEntityManager() {
    }

    public void addImplementation(GroupEntityManager implementation, boolean master) {
        if (implementation == null)
            throw new NullPointerException("implementation");
        implementations.add(implementation);
        if (master)
            this.master = implementation;
    }

    public GroupEntityManager getMaster() {
        if (master == null)
            throw new IllegalStateException("master implementation isn't set.");
        return master;
    }

    @Override
    public void insertGroup(Group group) {
        getMaster().insertGroup(group);
    }

    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        getMaster().updateGroup(updatedGroup);
    }

    @Override
    public void deleteGroup(String groupId) {
        for (GroupEntityManager impl : implementations)
            impl.deleteGroup(groupId);
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        for (GroupEntityManager impl : implementations) {
            GroupEntity user = impl.findGroupById(groupId);
            if (user != null)
                return user;
        }
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        long count = 0;
        for (GroupEntityManager impl : implementations)
            count += impl.findGroupCountByQueryCriteria(query);
        return count;
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        Set<Group> union = new LinkedHashSet<Group>();
        if (page == null) {
            for (GroupEntityManager impl : implementations) {
                List<Group> list = impl.findGroupByQueryCriteria(query, null);
                union.addAll(list);
            }
        } else {
            int firstResult = page.getFirstResult();
            int maxResults = page.getMaxResults();

            int beginIndex = 0;
            for (GroupEntityManager impl : implementations) {

                int localOffset = firstResult - beginIndex;
                if (localOffset < 0)
                    localOffset = 0;

                int max = maxResults - union.size();
                if (max <= 0)
                    break;

                union.addAll(impl.findGroupByQueryCriteria(query, new Page(localOffset, max)));
                beginIndex += impl.findGroupCountByQueryCriteria(query);
            }
        }
        List<Group> groups = new ArrayList<Group>(union);
        return groups;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        Set<Group> union = new LinkedHashSet<>();
        for (GroupEntityManager impl : implementations) {
            List<Group> list = impl.findGroupsByUser(userId);
            union.addAll(list);
        }
        List<Group> groups = new ArrayList<Group>(union);
        return groups;
    }

}
