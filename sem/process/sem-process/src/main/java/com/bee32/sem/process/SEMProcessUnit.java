package com.bee32.sem.process;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.PassToNext;

public class SEMProcessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AllowList.class);
        add(MultiLevel.class);
        add(PassToNext.class);
    }

}
