package com.bee32.sem.world;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.world.monetary.MonetaryUnit;
import com.bee32.sem.world.thing.ThingUnit;

@ImportUnit({//
/*    */ThingUnit.class, //
        MonetaryUnit.class, //
})
public class SEMWorldUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
