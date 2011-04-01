package com.bee32.plover.orm.feaCat;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class Animals
        extends PersistenceUnit {

    public Animals() {
        super("animals");
    }

    protected void preamble() {
        add(Cat.class);
        add(Tiger.class);
    }

    static final Animals instance = new Animals();

    public static Animals getInstance() {
        return instance;
    }

}
