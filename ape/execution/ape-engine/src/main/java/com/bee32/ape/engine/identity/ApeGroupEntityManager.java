package com.bee32.ape.engine.identity;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.plover.criteria.hibernate.Equals;

public class ApeGroupEntityManager
        extends GroupEntityManager
        implements IApeActivitiAdapter {

    static final Logger logger = LoggerFactory.getLogger(ApeGroupEntityManager.class);

    @Override
    public GroupEntity createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertGroup(Group group) {
        String icsfName = group.getId() + GROUP_EXT;

        com.bee32.icsf.principal.Principal _principal = ctx.data.access(icsfPrincipalType).getByName(icsfName);
        if (_principal != null) {
            logger.error("Group is already existed: " + icsfName);
            return;
        }

        com.bee32.icsf.principal.Role _group = new com.bee32.icsf.principal.Role();
        _group.setName(icsfName);
        _group.setFullName(group.getName());
        ctx.data.access(icsfGroupType).save(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        String name = updatedGroup.getId();
        String icsfName = name + GROUP_EXT;

        com.bee32.icsf.principal.Role _group = ctx.data.access(icsfGroupType).getByName(icsfName);
        if (_group == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("Group isn't existed: " + name);

        _group.setFullName(updatedGroup.getName());
        // updatedGroup.getType();

        ctx.data.access(icsfGroupType).update(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteGroup(String groupId) {
        String icsfName = groupId + GROUP_EXT;

        ctx.data.access(com.bee32.icsf.principal.Role.class)//
                .findAndDelete(new Equals("name", icsfName));
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
        String icsfName = groupId + GROUP_EXT;

        com.bee32.icsf.principal.Role icsfGroup = ctx.data.access(icsfGroupType).getByName(icsfName);
        if (icsfGroup == null)
            return null;
        else
            return ActivitiIdentityAdapters.icsfGroup2activitiGroup(icsfGroup);
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
