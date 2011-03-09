package com.bee32.sem.process.verify.builtin.dao;

import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.sem.process.verify.builtin.MultiLevelAllowList;

public class MultiLevelAllowListDao
        extends HibernateEntityRepository<MultiLevelAllowList, Integer> {

    public MultiLevelAllowListDao() {
        super(MultiLevelAllowList.class, Integer.class);
    }

}
