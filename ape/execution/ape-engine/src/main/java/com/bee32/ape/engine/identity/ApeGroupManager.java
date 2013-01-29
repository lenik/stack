package com.bee32.ape.engine.identity;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupManager;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.plover.criteria.hibernate.Equals;

public class ApeGroupManager
        extends GroupManager
        implements IApeActivitiAdapter {

    /**
     * @throws DataAccessException
     */
    @Override
    public Group createNewGroup(String groupId) {
        com.bee32.icsf.principal.Group _group = new com.bee32.icsf.principal.Group();
        _group.setName(groupId);
        ctx.data.access(icsfGroupType).save(_group);
        return ActivitiIdentityAdapters.icsfGroup2activitiGroup(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertGroup(Group group) {
        com.bee32.icsf.principal.Group _group = new com.bee32.icsf.principal.Group();
        _group.setName(group.getId());
        _group.setFullName(group.getName());
        ctx.data.access(icsfGroupType).save(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        String name = updatedGroup.getId();
        com.bee32.icsf.principal.Group _group = ctx.data.access(icsfGroupType).getByName(name);
        if (_group == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("Group isn't existed: " + updatedGroup.getId());

        _group.setFullName(updatedGroup.getName());
        // updatedGroup.getType();

        ctx.data.access(icsfGroupType).update(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteGroup(String groupId) {
        ctx.data.access(com.bee32.icsf.principal.Group.class)//
                .findAndDelete(new Equals("name", groupId));
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        return new ApeGroupQuery();
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        ApeGroupQuery _query = new ApeGroupQuery(query);
        return _query.listPage(page.getFirstResult(), page.getMaxResults());
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        ApeGroupQuery _query = new ApeGroupQuery(query);
        return _query.count();
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        com.bee32.icsf.principal.Group icsfGroup = ctx.data.access(icsfGroupType).getByName(groupId);
        if (icsfGroup == null)
            return null;
        else
            return ActivitiIdentityAdapters.icsfGroup2ActivitiGroupEntity(icsfGroup);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        ApeGroupQuery query = new ApeGroupQuery();
        query.groupMember(userId);
        return query.list();
    }

    @Override
    public List<Group> findPotentialStarterUsers(String proceDefId) {
        ApeGroupQuery query = new ApeGroupQuery();
        query.potentialStarter(proceDefId);
        return query.list();
    }

}
