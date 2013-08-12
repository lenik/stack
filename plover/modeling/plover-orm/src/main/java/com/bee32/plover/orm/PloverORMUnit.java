package com.bee32.plover.orm;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * Plover ORM (Core) Unit
 */
public class PloverORMUnit
        extends PersistenceUnit {

    @Override
    public int getPriority() {
        return SYSTEM_PRIORITY + 1;
    }

    @Override
    protected void preamble() {
        add(PloverConf.class);
    }

}
