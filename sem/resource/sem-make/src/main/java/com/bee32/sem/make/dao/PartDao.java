package com.bee32.sem.make.dao;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.make.entity.Part;

public class PartDao
        extends EntityDao<Part, Integer> {

    @Override
    protected void preSave(Part entity) {
        super.preSave(entity);
    }

}
