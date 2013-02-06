package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.User;

public class UserIdComparator
        extends StringPropertyComparator<User> {

    @Override
    protected String getTheProperty(User user) {
        return user.getId();
    }

    private static final UserIdComparator INSTANCE = new UserIdComparator();

    public static UserIdComparator getInstance() {
        return INSTANCE;
    }

}
