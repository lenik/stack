package com.bee32.ape.engine.identity.icsf;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.plover.criteria.hibernate.Equals;

public class IcsfGroupEntityManager
        extends GroupEntityManager
        implements IApeActivitiAdapter {

    static final Logger logger = LoggerFactory.getLogger(IcsfGroupEntityManager.class);

    @Override
    public GroupEntity createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertGroup(Group group) {
        String icsfName = group.getId() + ROLE_EXT;

        com.bee32.icsf.principal.Principal _principal = ctx.data.access(icsfPrincipalType).getByName(icsfName);
        if (_principal != null) {
            logger.error("Group is already existed: " + icsfName);
            return;
        }

        com.bee32.icsf.principal.Role _group = new com.bee32.icsf.principal.Role();
        _group.setName(icsfName);
        _group.setFullName(group.getName());
        ctx.data.access(icsfRoleType).save(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        String name = updatedGroup.getId();
        String icsfName = name + ROLE_EXT;

        com.bee32.icsf.principal.Role _group = ctx.data.access(icsfRoleType).getByName(icsfName);
        if (_group == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("Group isn't existed: " + name);

        _group.setFullName(updatedGroup.getName());
        // updatedGroup.getType();

        ctx.data.access(icsfRoleType).update(_group);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteGroup(String groupId) {
        String icsfName = groupId + ROLE_EXT;

        ctx.data.access(com.bee32.icsf.principal.Role.class)//
                .findAndDelete(new Equals("name", icsfName));
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        IcsfGroupQuery_G _query = new IcsfGroupQuery_G(query);
        return _query.listPage(page.getFirstResult(), page.getMaxResults());
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        IcsfGroupQuery_G _query = new IcsfGroupQuery_G(query);
        return _query.count();
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        String icsfName = groupId + ROLE_EXT;

        com.bee32.icsf.principal.Role icsfRole = ctx.data.access(icsfRoleType).getByName(icsfName);
        if (icsfRole == null)
            return null;
        else
            return IcsfIdentityAdapters.icsfRole2activitiGroup(icsfRole);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        IcsfGroupQuery_G query = new IcsfGroupQuery_G();
        query.groupMember(userId);
        return query.list();
    }

    @Override
    public List<Group> findPotentialStarterUsers(String proceDefId) {
        IcsfGroupQuery_G query = new IcsfGroupQuery_G();
        query.potentialStarter(proceDefId);
        return query.list();
    }

}
