package com.bee32.sem.world.monetary;

import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * （通用）货币支持系统数据单元
 *
 * <p lang="en">
 * (General) Monetary Unit
 */
public class MonetaryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(FxrRecord.class);
    }

}
