package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.User;

public class UserEmailComparator
        extends StringPropertyComparator<User> {

    @Override
    protected String getTheProperty(User user) {
        return user.getEmail();
    }

    private static final UserEmailComparator INSTANCE = new UserEmailComparator();

    public static UserEmailComparator getInstance() {
        return INSTANCE;
    }

}
