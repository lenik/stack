package com.bee32.sem.world.thing;

import javax.persistence.Entity;

import com.bee32.plover.orm.unit.PersistenceUnit;

@Entity
public class SEMWorldThingUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Unit.class);
        add(UnitConv.class);
    }

}
