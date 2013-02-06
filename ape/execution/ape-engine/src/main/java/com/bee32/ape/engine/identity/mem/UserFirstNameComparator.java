package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.User;

public class UserFirstNameComparator
        extends StringPropertyComparator<User> {

    @Override
    protected String getTheProperty(User user) {
        return user.getFirstName();
    }

    private static final UserFirstNameComparator INSTANCE = new UserFirstNameComparator();

    public static UserFirstNameComparator getInstance() {
        return INSTANCE;
    }

}
