package com.bee32.ape.engine.identity.icsf;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.bee32.icsf.principal.User;

public abstract class AbstractIcsfTypeMapping
        implements IIcsfTypeMapping {

    String groupExtension = getGroupExtension();

    protected abstract String getGroupExtension();

    @Override
    public String toIcsfGroupName(String plainGroupName) {
        if (plainGroupName == null)
            throw new NullPointerException("plainGroupName");
        return plainGroupName + groupExtension;
    }

    @Override
    public String toPlainGroupName(String icsfGroupName) {
        if (icsfGroupName == null)
            throw new NullPointerException("icsfGroupName");
        String plainName = icsfGroupName;
        if (plainName.endsWith(groupExtension))
            plainName = plainName.substring(0, plainName.length() - groupExtension.length());
        return plainName;
    }

    @Override
    public UserEntity convertUser(User icsfUser) {
        UserEntity activitiUser = new UserEntity();
        activitiUser.setId(icsfUser.getName());
        // userEntity.setRevision(icsfUser.getVersion());
        activitiUser.setFirstName(icsfUser.getFirstName());
        activitiUser.setLastName(icsfUser.getLastName());
        // activitiUser.setEmail(icsfUser.getPreferredEmail());
        return activitiUser;
    }

    @Override
    public List<org.activiti.engine.identity.User> convertUserList(List<User> icsfUsers) {
        List<org.activiti.engine.identity.User> activitiUsers = new ArrayList<>();
        for (User icsfUser : icsfUsers) {
            UserEntity activitiUser = convertUser(icsfUser);
            activitiUsers.add(activitiUser);
        }
        return activitiUsers;
    }

}
