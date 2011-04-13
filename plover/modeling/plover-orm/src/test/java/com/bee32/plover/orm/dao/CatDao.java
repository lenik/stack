package com.bee32.plover.orm.dao;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.feaCat.Cat;

public class CatDao
        extends EntityDao<Cat, Long> {

    @Override
    public boolean populate(Cat cat, IStruct struct)
            throws BuildException {
        cat.setName((String) struct.getScalar("name"));
        cat.setColor((String) struct.getScalar("color"));
        return true;
    }

}
