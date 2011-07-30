package com.bee32.plover.orm.feaCat;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class AnimalUnit
        extends PersistenceUnit {

    protected void preamble() {
        add(Cat.class);
        add(Tiger.class);
        add(CatFavTag.class);
    }

    static final AnimalUnit instance = new AnimalUnit();

    public static AnimalUnit getInstance() {
        return instance;
    }

}
