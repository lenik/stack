package com.bee32.ape.engine.identity;

import java.util.Collection;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.GroupQueryProperty;
import org.activiti.engine.impl.persistence.entity.GroupEntity;

public class GroupEntityUtils {

    public static void populate(GroupEntity dst, GroupEntity src) {
        dst.setId(src.getId());
        dst.setRevision(src.getRevision());
        dst.setName(src.getName());
        dst.setType(src.getType());
    }

    /**
     * Match the group info.
     *
     * The member users and process info isn't matched.
     */
    public static boolean match(Group group, GroupQueryImpl query) {
        if (MatchUtils.notMatch(query.getId(), group.getId()))
            return false;
        if (MatchUtils.notMatch(query.getName(), group.getName()))
            return false;
        if (MatchUtils.notLike(query.getNameLike(), group.getName()))
            return false;
        if (MatchUtils.notMatch(query.getType(), group.getType()))
            return false;
        return true;
    }

    public static GroupQueryProperty getOrderProperty(String orderBy) {
        if (orderBy == null)
            return null;
        String column = ActivitiOrderUtils.getOrderColumn(orderBy);

        if (GroupQueryProperty.GROUP_ID.getName().equals(column))
            return GroupQueryProperty.GROUP_ID;

        if (GroupQueryProperty.NAME.getName().equals(column))
            return GroupQueryProperty.NAME;

        if (GroupQueryProperty.TYPE.getName().equals(column))
            return GroupQueryProperty.TYPE;

        return null;
    }

    public static String toString(Collection<? extends Group> groups) {
        StringBuilder sb = new StringBuilder(groups.size() * 30);
        for (Group group : groups) {
            if (sb.length() != 0)
                sb.append(", ");
            sb.append(group.getId());
        }
        return sb.toString();
    }

}
