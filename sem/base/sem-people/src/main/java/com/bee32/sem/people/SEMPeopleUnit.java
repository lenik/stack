package com.bee32.sem.people;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

@ImportUnit({ IcsfIdentityUnit.class })
public class SEMPeopleUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
