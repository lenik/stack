package com.bee32.plover.orm.entity;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;

public class CatRepo
        extends HibernateEntityRepository<Cat, Long> {

    public CatRepo() {
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
