package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class Animals
        extends PersistenceUnit {

    public Animals() {
        super("animals");

        addPersistedClass(Cat.class);
        addPersistedClass(Tiger.class);
    }

    static final Animals instance = new Animals();

    public static Animals getInstance() {
        return instance;
    }

}
