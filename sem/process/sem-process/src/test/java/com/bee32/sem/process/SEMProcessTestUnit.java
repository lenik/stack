package com.bee32.sem.process;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.verify.testbiz.AttackMission;

@ImportUnit(SEMProcessUnit.class)
public class SEMProcessTestUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AttackMission.class);
    }

}
