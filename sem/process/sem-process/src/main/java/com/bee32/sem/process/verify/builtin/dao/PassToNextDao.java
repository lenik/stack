package com.bee32.sem.process.verify.builtin.dao;

import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.sem.process.verify.builtin.PassToNext;

public class PassToNextDao
        extends HibernateEntityRepository<PassToNext, Integer> {

    public PassToNextDao() {
        super(PassToNext.class, Integer.class);
    }

}
