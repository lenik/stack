package org.activiti.engine.impl;

import com.bee32.ape.engine.identity.PropertiesStringBuilder;

public class GroupQueryUtils {

    public static String format(GroupQueryImpl query) {
        PropertiesStringBuilder sb = new PropertiesStringBuilder();
        sb.put("id", query.getId());
        sb.put("name", query.getName());
        sb.put("nameLike", query.getNameLike());
        sb.put("type", query.getType());
        sb.put("userId", query.getUserId());
        sb.put("procDefId", query.getProcDefId());
        sb.put("orderBy", query.getOrderBy());
        sb.put("parameter", query.getParameter());
        return sb.toString();
    }

}
