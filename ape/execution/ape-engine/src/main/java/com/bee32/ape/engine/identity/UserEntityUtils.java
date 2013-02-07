package com.bee32.ape.engine.identity;

import java.util.Collection;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.impl.persistence.entity.UserEntity;

public class UserEntityUtils {

    public static void populate(UserEntity dst, UserEntity src) {
        dst.setId(src.getId());
        dst.setRevision(src.getRevision());

        dst.setFirstName(src.getFirstName());
        dst.setLastName(src.getLastName());
        dst.setPassword(src.getPassword());
        dst.setEmail(src.getEmail());

        dst.setPicture(src.getPicture());
    }

    /**
     * Match the user part.
     *
     * groupId and proceDefId is not included.
     */
    public static boolean match(User user, UserQueryImpl query) {
        if (MatchUtils.notMatch(query.getId(), user.getId()))
            return false;

        if (MatchUtils.notMatch(query.getFirstName(), user.getFirstName()))
            return false;
        if (MatchUtils.notLike(query.getFirstNameLike(), user.getFirstName()))
            return false;
        if (MatchUtils.notMatch(query.getLastName(), user.getLastName()))
            return false;
        if (MatchUtils.notLike(query.getLastNameLike(), user.getLastName()))
            return false;

        if (MatchUtils.notMatch(query.getEmail(), user.getEmail()))
            return false;
        if (MatchUtils.notLike(query.getEmailLike(), user.getEmail()))
            return false;

        return true;
    }

    public static UserQueryProperty getOrderProperty(String orderBy) {
        if (orderBy == null)
            return null;
        String column = ActivitiOrderUtils.getOrderColumn(orderBy);

        if (UserQueryProperty.USER_ID.getName().equals(column))
            return UserQueryProperty.USER_ID;

        if (UserQueryProperty.FIRST_NAME.getName().equals(column))
            return UserQueryProperty.FIRST_NAME;

        if (UserQueryProperty.LAST_NAME.getName().equals(column))
            return UserQueryProperty.LAST_NAME;

        if (UserQueryProperty.EMAIL.getName().equals(column))
            return UserQueryProperty.EMAIL;

        return null;
    }

    public static String toString(Collection<? extends User> users) {
        StringBuilder sb = new StringBuilder(users.size() * 30);
        for (User user : users) {
            if (sb.length() != 0)
                sb.append(", ");
            sb.append(user.getId());
        }
        return sb.toString();
    }

}
