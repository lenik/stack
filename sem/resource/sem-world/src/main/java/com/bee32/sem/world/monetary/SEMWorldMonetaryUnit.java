package com.bee32.sem.world.monetary;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class SEMWorldMonetaryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(FxrRecord.class);
    }

}
