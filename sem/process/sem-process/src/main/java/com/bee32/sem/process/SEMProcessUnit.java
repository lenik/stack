package com.bee32.sem.process;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.Level;
import com.bee32.sem.process.verify.builtin.PassStep;
import com.bee32.sem.process.verify.builtin.PassToNext;

@ImportUnit(IcsfIdentityUnit.class)
public class SEMProcessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AllowList.class);
        add(MultiLevel.class);
        add(Level.class);
        add(PassToNext.class);
        add(PassStep.class);
    }

}
