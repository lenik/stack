package com.bee32.ape.engine.identity;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.bee32.ape.engine.base.AbstractApeQuery;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.entity.Entity;

public class ApeGroupQuery
        extends AbstractApeQuery<GroupQuery, Group>
        implements GroupQuery {

    String id;
    String name;
    String nameLike;
    String type;
    String memberUserId;
    String procDefId;

    public ApeGroupQuery() {
        super(com.bee32.icsf.principal.Group.class);
    }

    public ApeGroupQuery(GroupQueryImpl o) {
        super(com.bee32.icsf.principal.Group.class, o);
        id = o.getId();
        name = o.getName();
        nameLike = o.getNameLike();
        type = o.getType();
        memberUserId = o.getUserId();
        // procDefId=o.procDefId;
    }

    @Override
    public GroupQuery groupId(String groupId) {
        this.id = groupId;
        return this;
    }

    @Override
    public GroupQuery groupName(String groupName) {
        this.name = groupName;
        return this;
    }

    @Override
    public GroupQuery groupNameLike(String groupNameLike) {
        this.nameLike = groupNameLike;
        return this;
    }

    @Override
    public GroupQuery groupType(String groupType) {
        this.type = groupType;
        return this;
    }

    @Override
    public GroupQuery groupMember(String groupMemberUserId) {
        this.memberUserId = groupMemberUserId;
        return this;
    }

    @Override
    public GroupQuery potentialStarter(String procDefId) {
        this.procDefId = procDefId;
        return this;
    }

    @Override
    public GroupQuery orderByGroupId() {
        setOrderProperty("name");
        return this;
    }

    @Override
    public GroupQuery orderByGroupName() {
        setOrderProperty("label");
        return this;
    }

    @Override
    public GroupQuery orderByGroupType() {
        // Not supported:
        // setOrderProperty("type");
        return this;
    }

    @Override
    protected CriteriaComposite compose() {
        CriteriaComposite composite = new CriteriaComposite();

        if (id != null)
            composite.add(new Equals("name", id + GROUP_EXT));

        if (name != null)
            composite.add(new Equals("label", name));
        if (nameLike != null)
            composite.add(new Like(true, "label", name));

        if (type != null)
            ;

        if (memberUserId != null) {
            User user = ctx.data.access(icsfUserType).getByName(memberUserId);
            int userId = user == null ? -1 : user.getId();

            composite.add(new Alias("memberUsers", "u"));
            composite.add(new Equals("u.id", userId));
        }

        String orderProperty = getOrderProperty();
        if (orderProperty != null) {
            Order order;
            if (isReverseOrder())
                order = Order.desc(orderProperty);
            else
                order = Order.asc(orderProperty);
            composite.add(order);
        }

        return composite;
    }

    @Override
    protected GroupEntity icsf2activiti(Entity<?> icsfEntity) {
        com.bee32.icsf.principal.Role icsfGroup = (com.bee32.icsf.principal.Role) icsfEntity;
        return ActivitiIdentityAdapters.icsfGroup2activitiGroup(icsfGroup);
    }

}
