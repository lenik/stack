package com.bee32.ape.engine.identity.composite;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;

public class CompositeGroupQuery
        extends AbstractCompositeQuery<GroupQuery, Group>
        implements GroupQuery {

    private static final long serialVersionUID = 1L;

    public CompositeGroupQuery() {
        super();
    }

    public CompositeGroupQuery(CompositeGroupQuery o) {
        super(o);
    }

    @Override
    public CompositeGroupQuery groupId(String groupId) {
        for (GroupQuery item : this)
            item.groupId(groupId);
        return this;
    }

    @Override
    public CompositeGroupQuery groupName(String groupName) {
        for (GroupQuery item : this)
            item.groupName(groupName);
        return this;
    }

    @Override
    public CompositeGroupQuery groupNameLike(String groupNameLike) {
        for (GroupQuery item : this)
            item.groupNameLike(groupNameLike);
        return this;
    }

    @Override
    public CompositeGroupQuery groupType(String groupType) {
        for (GroupQuery item : this)
            item.groupType(groupType);
        return this;
    }

    @Override
    public CompositeGroupQuery groupMember(String groupMemberUserId) {
        for (GroupQuery item : this)
            item.groupMember(groupMemberUserId);
        return this;
    }

    @Override
    public CompositeGroupQuery potentialStarter(String procDefId) {
        for (GroupQuery item : this)
            item.potentialStarter(procDefId);
        return this;
    }

    @Override
    public CompositeGroupQuery orderByGroupId() {
        for (GroupQuery item : this)
            item.orderByGroupId();
        return this;
    }

    @Override
    public CompositeGroupQuery orderByGroupName() {
        for (GroupQuery item : this)
            item.orderByGroupName();
        return this;
    }

    @Override
    public CompositeGroupQuery orderByGroupType() {
        for (GroupQuery item : this)
            item.orderByGroupType();
        return this;
    }

}
