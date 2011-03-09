package com.bee32.sem.process.verify.builtin.dao;

import com.bee32.plover.orm.entity.AbstractDao;
import com.bee32.sem.process.verify.builtin.AllowList;

public class AllowListDao
        extends AbstractDao<AllowList, Integer> {

    public AllowListDao() {
        super(AllowList.class, Integer.class);
    }

}
