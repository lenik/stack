package com.bee32.sem.world.thing;

import javax.persistence.Entity;

import com.bee32.plover.orm.unit.PersistenceUnit;

@Entity
public class ThingUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Unit.class);
        add(UnitConv.class);
    }

}
