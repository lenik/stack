package com.bee32.sem.world.thing;

import javax.persistence.Entity;

import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * （通用）物品支持数据单元
 *
 * <p lang="en">
 * (General) Things Support Unit
 */
@Entity
public class ThingUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Unit.class);
        add(UnitConv.class);
    }

}
