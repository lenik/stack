package com.bee32.sem.process.verify.impl;

import com.bee32.plover.orm.entity.HibernateEntityRepository;

public class PassToNextRepo
        extends HibernateEntityRepository<PassToNext, Integer> {

    public PassToNextRepo() {
        super(PassToNext.class, Integer.class);
    }

}
