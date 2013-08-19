package com.bee32.sem.module;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * SEM 基系统数据单元
 *
 * <p lang="en">
 * SEM Base Unit
 */
@ImportUnit(IcsfAccessUnit.class)
public class SEMBaseUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
