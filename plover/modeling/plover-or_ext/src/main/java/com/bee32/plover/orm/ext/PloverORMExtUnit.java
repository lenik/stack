package com.bee32.plover.orm.ext;

import com.bee32.plover.orm.ext.meta.EntityColumn;
import com.bee32.plover.orm.ext.meta.EntityInfo;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class PloverORMExtUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(EntityInfo.class);
        add(EntityColumn.class);
    }

}
