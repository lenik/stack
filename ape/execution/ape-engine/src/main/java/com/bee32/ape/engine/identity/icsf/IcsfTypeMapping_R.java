package com.bee32.ape.engine.identity.icsf;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.bee32.ape.engine.identity.ActivitiGroupType;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class IcsfTypeMapping_R
        extends AbstractIcsfTypeMapping {

    @Override
    public Class<? extends Principal> getIcsfGroupType() {
        return Role.class;
    }

    @Override
    public Role newIcsfGroup() {
        return new Role();
    }

    @Override
    protected String getGroupExtension() {
        return "role";
    }

    @Override
    public ICriteriaElement getMemberUsersAlias(String alias) {
        return new Alias("responsibleUsers", alias);
    }

    @Override
    public ICriteriaElement getUserGroupsAlias(String alias) {
        return new Alias("assignedRoles", alias);
    }

    public List<org.activiti.engine.identity.Group> convertGroupList(List<? extends Principal> _icsfRoles) {
        @SuppressWarnings("unchecked")
        List<Role> icsfRoles = (List<Role>) _icsfRoles;
        return convertRoleGroupList(icsfRoles);
    }

    List<org.activiti.engine.identity.Group> convertRoleGroupList(List<Role> icsfRoles) {
        List<org.activiti.engine.identity.Group> activitiGroups = new ArrayList<>(icsfRoles.size());
        for (Role icsfRole : icsfRoles) {
            GroupEntity activitiGroup = convertGroup(icsfRole);
            activitiGroups.add(activitiGroup);
        }
        return activitiGroups;
    }

    @Override
    public GroupEntity convertGroup(Principal icsfRole) {
        return convertRoleGroup((Role) icsfRole);
    }

    GroupEntity convertRoleGroup(Role icsfRole) {
        String plainName = toPlainGroupName(icsfRole.getName());

        GroupEntity activitiGroup = new GroupEntity();
        activitiGroup.setId(plainName);
        // groupEntity.setRevision(icsfGroup.getVersion());
        activitiGroup.setName(icsfRole.getDisplayName());

        activitiGroup.setType(ActivitiGroupType.ASSIGNMENT);
        return activitiGroup;
    }

    @Override
    public void addMembership(User icsfUser, Principal icsfGroup) {
        Role icsfRole = (Role) icsfGroup;
        icsfUser.addAssignedRole(icsfRole);
        icsfRole.addResponsibleUser(icsfUser);
    }

    @Override
    public void removeMembership(User icsfUser, Principal icsfGroup) {
        Role icsfRole = (Role) icsfGroup;
        icsfUser.removeAssignedRole(icsfRole);
        icsfRole.removeResponsibleUser(icsfUser);
    }

}
