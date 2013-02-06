package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.User;

public class UserLastNameComparator
        extends StringPropertyComparator<User> {

    @Override
    protected String getTheProperty(User user) {
        return user.getLastName();
    }

    private static final UserLastNameComparator INSTANCE = new UserLastNameComparator();

    public static UserLastNameComparator getInstance() {
        return INSTANCE;
    }

}
