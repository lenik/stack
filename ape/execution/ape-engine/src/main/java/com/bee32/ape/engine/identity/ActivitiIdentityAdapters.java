package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.User;

class ActivitiIdentityAdapters {

    public static org.activiti.engine.identity.User icsfUser2activitiUser(User icsfUser) {
        org.activiti.engine.identity.User activitiUser = new SimpleUser();
        activitiUser.setId(icsfUser.getName());
        activitiUser.setFirstName(icsfUser.getFullName());
        activitiUser.setEmail(icsfUser.getPreferredEmail());
        return activitiUser;
    }

    public static UserEntity icsfUser2activitiUserEntity(User icsfUser) {
        UserEntity userEntity = new UserEntity(icsfUser.getName());
        // userEntity.setRevision(icsfUser.getVersion());
        userEntity.setFirstName(icsfUser.getFullName());
        userEntity.setEmail(icsfUser.getPreferredEmail());
        return userEntity;
    }

    public static org.activiti.engine.identity.Group icsfGroup2activitiGroup(Group icsfGroup) {
        org.activiti.engine.identity.Group activitiGroup = new SimpleGroup();
        activitiGroup.setId(icsfGroup.getName());
        activitiGroup.setName(icsfGroup.getLabel());
        // group.setType("type");
        return activitiGroup;
    }

    public static GroupEntity icsfGroup2ActivitiGroupEntity(Group icsfGroup) {
        GroupEntity groupEntity = new GroupEntity(icsfGroup.getName());
        // groupEntity.setRevision(icsfGroup.getVersion());
        groupEntity.setName(icsfGroup.getFullName());
        // groupEntity.setType("");
        return groupEntity;
    }

}
