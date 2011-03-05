package com.bee32.sem.process.verify.impl;

import com.bee32.plover.orm.entity.HibernateEntityRepository;

public class AllowListRepo
        extends HibernateEntityRepository<AllowList, Integer> {

    public AllowListRepo() {
        super(AllowList.class, Integer.class);
    }

}
