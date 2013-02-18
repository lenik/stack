package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;

class ActivitiIdentityAdapters
        implements IApeActivitiAdapter {

    public static UserEntity icsfUser2activitiUser(User icsfUser) {
        UserEntity activitiUser = new UserEntity();
        activitiUser.setId(icsfUser.getName());
        // userEntity.setRevision(icsfUser.getVersion());
        activitiUser.setFirstName(firstName(icsfUser));
        activitiUser.setLastName(lastName(icsfUser));
        activitiUser.setEmail(icsfUser.getPreferredEmail());
        return activitiUser;
    }

    public static GroupEntity icsfGroup2activitiGroup(Role icsfGroup) {
        String plainName = icsfGroup.getName();
        if (plainName.endsWith(GROUP_EXT))
            plainName = plainName.substring(0, plainName.length() - 4);

        GroupEntity activitiGroup = new GroupEntity();
        activitiGroup.setId(plainName);
        // groupEntity.setRevision(icsfGroup.getVersion());
        activitiGroup.setName(icsfGroup.getLabel());
        // group.setType("type");
        return activitiGroup;
    }

    static String firstName(User icsfUser) {
        String fullName = icsfUser.getFullName();
        if (fullName == null)
            return icsfUser.getName();

        fullName = fullName.trim();
        if (fullName.isEmpty())
            return icsfUser.getName();

        int space = fullName.indexOf(' ');
        if (space == -1)
            return fullName;
        else
            return fullName.substring(0, space);
    }

    static String lastName(User icsfUser) {
        String fullName = icsfUser.getFullName();
        if (fullName == null)
            return icsfUser.getName();

        fullName = fullName.trim();
        if (fullName.isEmpty())
            return icsfUser.getName();

        int space = fullName.indexOf(' ');
        if (space == -1)
            return "";

        String lastName = fullName.substring(space + 1).trim();
        if (lastName.isEmpty())
            return "";
        else
            return lastName;
    }

}
