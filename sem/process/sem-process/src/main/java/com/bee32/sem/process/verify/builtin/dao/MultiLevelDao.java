package com.bee32.sem.process.verify.builtin.dao;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.builtin.MultiLevel;

public class MultiLevelDao
        extends EntityDao<MultiLevel, Integer> {

    public MultiLevelDao() {
        super(MultiLevel.class, Integer.class);
    }

}
