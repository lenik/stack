package com.bee32.plover.orm;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class PloverORMUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(PloverConf.class);
    }

}
