package com.bee32.sem.process.verify.impl;

import com.bee32.plover.orm.entity.HibernateEntityRepository;

public class MultiLevelAllowListRepo
        extends HibernateEntityRepository<MultiLevelAllowList, Integer> {

    public MultiLevelAllowListRepo() {
        super(MultiLevelAllowList.class, Integer.class);
    }

}
