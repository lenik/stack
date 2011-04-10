package com.bee32.sem.process.verify.builtin.dao;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.builtin.PassToNext;

public class PassToNextDao
        extends EntityDao<PassToNext, Integer> {

    public PassToNextDao() {
        super(PassToNext.class, Integer.class);
    }

}
