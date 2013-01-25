package com.bee32.sem.process.ape;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.User;

class ActivitiAdapters {

    public static org.activiti.engine.identity.User semUser2activitiUser(User semUser) {
        org.activiti.engine.identity.User activitiUser = new SimpleUser();
        activitiUser.setId(semUser.getName());
        activitiUser.setFirstName(semUser.getFullName());
        activitiUser.setEmail(semUser.getPreferredEmail());
        return activitiUser;
    }

    public static UserEntity semUser2activitiUserEntity(User semUser) {
        UserEntity userEntity = new UserEntity(semUser.getName());
        // userEntity.setRevision(semUser.getVersion());
        userEntity.setFirstName(semUser.getFullName());
        userEntity.setEmail(semUser.getPreferredEmail());
        return userEntity;
    }

    public static org.activiti.engine.identity.Group semGroup2activitiGroup(Group semGroup) {
        org.activiti.engine.identity.Group activitiGroup = new SimpleGroup();
        activitiGroup.setId(semGroup.getName());
        activitiGroup.setName(semGroup.getLabel());
        // group.setType("type");
        return activitiGroup;
    }

    public static GroupEntity semGroup2ActivitiGroupEntity(Group semGroup) {
        GroupEntity groupEntity = new GroupEntity(semGroup.getName());
        // groupEntity.setRevision(semGroup.getVersion());
        groupEntity.setName(semGroup.getFullName());
        // groupEntity.setType("");
        return groupEntity;
    }

}
