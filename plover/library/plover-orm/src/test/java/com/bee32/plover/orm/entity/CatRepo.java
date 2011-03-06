package com.bee32.plover.orm.entity;

import java.util.Map;

import com.bee32.plover.arch.BuildException;

public class CatRepo
        extends HibernateEntityRepository<Cat, Long> {

    public CatRepo() {
        super(Cat.class, Long.class);
    }

    @Override
    public boolean populate(Cat cat, Map<String, ?> struct)
            throws BuildException {
        cat.setName((String) struct.get("name"));
        cat.setColor((String) struct.get("color"));
        return true;
    }

}
