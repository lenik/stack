package com.bee32.ape.engine.identity.icsf;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.bee32.ape.engine.identity.ActivitiGroupType;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class IcsfTypeMapping_G
        extends AbstractIcsfTypeMapping {

    @Override
    public Class<? extends Principal> getIcsfGroupType() {
        return Group.class;
    }

    @Override
    public Group newIcsfGroup() {
        return new Group();
    }

    @Override
    protected String getGroupExtension() {
        return "";
    }

    @Override
    public ICriteriaElement getMemberUsersAlias(String alias) {
        return new Alias("memberUsers", alias);
    }

    @Override
    public ICriteriaElement getUserGroupsAlias(String alias) {
        return new Alias("assignedGroups", alias);
    }

    public List<org.activiti.engine.identity.Group> convertGroupList(List<? extends Principal> _icsfGroups) {
        @SuppressWarnings("unchecked")
        List<Group> icsfGroups = (List<Group>) _icsfGroups;
        return convertGroupGroupList(icsfGroups);
    }

    List<org.activiti.engine.identity.Group> convertGroupGroupList(List<Group> icsfGroups) {
        List<org.activiti.engine.identity.Group> activitiGroups = new ArrayList<>(icsfGroups.size());
        for (Group icsfGroup : icsfGroups) {
            GroupEntity activitiGroup = convertGroup(icsfGroup);
            activitiGroups.add(activitiGroup);
        }
        return activitiGroups;
    }

    @Override
    public GroupEntity convertGroup(Principal icsfGroup) {
        return convertGroupGroup((Group) icsfGroup);
    }

    GroupEntity convertGroupGroup(Group icsfGroup) {
        String plainName = toPlainGroupName(icsfGroup.getName());

        GroupEntity activitiGroup = new GroupEntity();
        activitiGroup.setId(plainName);
        // groupEntity.setRevision(icsfGroup.getVersion());
        activitiGroup.setName(icsfGroup.getDisplayName());

        activitiGroup.setType(ActivitiGroupType.ASSIGNMENT);
        return activitiGroup;
    }

}
