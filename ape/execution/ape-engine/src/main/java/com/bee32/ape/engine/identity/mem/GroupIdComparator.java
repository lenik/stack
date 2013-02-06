package com.bee32.ape.engine.identity.mem;

import org.activiti.engine.identity.Group;

public class GroupIdComparator
        extends StringPropertyComparator<Group> {

    @Override
    protected String getTheProperty(Group obj) {
        return obj.getId();
    }

    private static final GroupIdComparator INSTANCE = new GroupIdComparator();

    public static GroupIdComparator getInstance() {
        return INSTANCE;
    }

}
