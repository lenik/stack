package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.Group;
import com.bee32.plover.orm.entity.AbstractDao;

public class GroupDao
        extends AbstractDao<Group, Long> {

    public GroupDao() {
        super(Group.class, Long.class);
    }

}
