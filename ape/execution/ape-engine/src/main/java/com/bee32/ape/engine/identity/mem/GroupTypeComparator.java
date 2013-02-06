package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.Group;

public class GroupTypeComparator
        extends StringPropertyComparator<Group> {

    @Override
    protected String getTheProperty(Group obj) {
        return obj.getType();
    }

    private static final GroupTypeComparator INSTANCE = new GroupTypeComparator();

    public static GroupTypeComparator getInstance() {
        return INSTANCE;
    }

}
