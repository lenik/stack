package com.bee32.sem.module;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

@ImportUnit(IcsfIdentityUnit.class)
public class SEMBaseUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
