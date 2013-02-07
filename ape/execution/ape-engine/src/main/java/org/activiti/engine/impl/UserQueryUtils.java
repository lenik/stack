package org.activiti.engine.impl;

import com.bee32.ape.engine.identity.PropertiesStringBuilder;

public class UserQueryUtils {

    public static String format(UserQueryImpl query) {
        PropertiesStringBuilder sb = new PropertiesStringBuilder();
        sb.put("id", query.getId());
        sb.put("firstName", query.getFirstName());
        sb.put("firstNameLike", query.getFirstNameLike());
        sb.put("lastName", query.getLastName());
        sb.put("lastNameLike", query.getLastNameLike());
        sb.put("email", query.getEmail());
        sb.put("emailLike", query.getEmailLike());
        sb.put("groupId", query.getGroupId());
        sb.put("procDefId", query.getProcDefId());
        sb.put("orderBy", query.getOrderBy());
        return sb.toString();
    }

}
