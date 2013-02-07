package com.bee32.ape.engine.identity.icsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.GroupQueryProperty;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.ape.engine.identity.ActivitiOrderUtils;
import com.bee32.ape.engine.identity.GroupEntityUtils;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;

public class IcsfGroupEntityManager
        extends GroupEntityManager
        implements IApeActivitiAdapter {

    static final Logger logger = LoggerFactory.getLogger(IcsfGroupEntityManager.class);

    private IIcsfTypeMapping icsfTypeMapping;

    public IcsfGroupEntityManager(IIcsfTypeMapping icsfTypeMapping) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        this.icsfTypeMapping = icsfTypeMapping;
    }

    @Override
    public GroupEntity createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertGroup(Group group) {
        String icsfName = icsfTypeMapping.toIcsfGroupName(group.getId());

        Principal icsfPrincipal = ctx.data.access(Principal.class).getByName(icsfName);
        if (icsfPrincipal != null) {
            logger.error("Group is already existed: " + icsfName);
            return;
        }

        Principal _role = icsfTypeMapping.newIcsfGroup();
        _role.setName(icsfName);
        _role.setFullName(group.getName());
        ctx.data.access(icsfTypeMapping.getIcsfGroupType()).save(_role);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateGroup(GroupEntity updatedGroup) {
        String icsfName = icsfTypeMapping.toIcsfGroupName(updatedGroup.getId());

        Principal icsfGroup = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).getByName(icsfName);
        if (icsfGroup == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("Group isn't existed: " + icsfName);

        icsfGroup.setFullName(updatedGroup.getName());
        // updatedGroup.getType();

        ctx.data.access(icsfTypeMapping.getIcsfGroupType()).update(icsfGroup);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteGroup(String groupId) {
        String icsfName = icsfTypeMapping.toIcsfGroupName(groupId);

        ctx.data.access(icsfTypeMapping.getIcsfGroupType())//
                .findAndDelete(new Equals("name", icsfName));
    }

    @Override
    public GroupEntity findGroupById(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        String icsfName = icsfTypeMapping.toIcsfGroupName(groupId);

        Principal icsfGroup = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).getByName(icsfName);
        if (icsfGroup == null)
            return null;
        else
            return icsfTypeMapping.convertGroup(icsfGroup);
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        CriteriaComposite criteria = compose(query);
        if (criteria == null)
            return 0L;

        return ctx.data.access(icsfTypeMapping.getIcsfGroupType()).count(criteria);
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        CriteriaComposite criteria = compose(query);
        if (criteria == null)
            return Collections.emptyList();

        Limit limit = null;
        if (page != null) {
            limit = new Limit(page.getFirstResult(), page.getMaxResults());
            criteria.add(limit);
        }
        List<Principal> icsfGroups = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).list(criteria);
        return icsfTypeMapping.convertGroupList(icsfGroups);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Principal> icsfGroups;
        icsfGroups = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).list(//
                icsfTypeMapping.getMemberUsersAlias("m"), //
                new Equals("m.name", userId));
        return icsfTypeMapping.convertGroupList(icsfGroups);
    }

    @Override
    public List<Group> findPotentialStarterUsers(String proceDefId) {
        List<Group> activitiGroups = new ArrayList<>();
        return activitiGroups;
    }

    public CriteriaComposite compose(GroupQueryImpl query) {
        CriteriaComposite composite = new CriteriaComposite();

        String id = query.getId();
        String name = query.getName();
        String nameLike = query.getNameLike();
        String type = query.getType();

        if (id != null)
            composite.add(new Equals("name", icsfTypeMapping.toIcsfGroupName(id)));

        if (name != null)
            composite.add(new Equals("label", name));
        if (nameLike != null)
            composite.add(new Like(true, "label", name));

        if (type != null)
            ;

        String memberUserId = query.getUserId();
        if (memberUserId != null) {
            User user = ctx.data.access(icsfUserType).getByName(memberUserId);
            int userId = user == null ? -1 : user.getId();

            composite.add(icsfTypeMapping.getMemberUsersAlias("m"));
            composite.add(new Equals("m.id", userId));
        }

        String orderBy = query.getOrderBy();
        if (orderBy != null) {
            String propertyName = null;

            GroupQueryProperty queryProperty = GroupEntityUtils.getOrderProperty(orderBy);
            if (queryProperty == GroupQueryProperty.GROUP_ID)
                propertyName = "name";
            else if (queryProperty == GroupQueryProperty.NAME)
                propertyName = "label";
            else if (queryProperty == GroupQueryProperty.TYPE)
                ;

            if (propertyName != null)
                if (ActivitiOrderUtils.isAscending(orderBy))
                    composite.add(Order.asc(propertyName));
                else
                    composite.add(Order.desc(propertyName));
        }

        return composite;
    }

}
