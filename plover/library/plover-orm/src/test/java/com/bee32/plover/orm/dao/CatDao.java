package com.bee32.plover.orm.dao;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.orm.entity.AbstractDao;
import com.bee32.plover.orm.entity.Cat;

public class CatDao
        extends AbstractDao<Cat, Long> {

    public CatDao() {
        super(Cat.class, Long.class);
    }

    @Override
    public boolean populate(Cat cat, IStruct struct)
            throws BuildException {
        cat.setName((String) struct.getScalar("name"));
        cat.setColor((String) struct.getScalar("color"));
        return true;
    }

}
