package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.Group;

public class GroupNameComparator
        extends StringPropertyComparator<Group> {

    @Override
    protected String getTheProperty(Group obj) {
        return obj.getName();
    }

    private static final GroupNameComparator INSTANCE = new GroupNameComparator();

    public static GroupNameComparator getInstance() {
        return INSTANCE;
    }

}
