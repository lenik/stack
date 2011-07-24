package com.bee32.sem.process;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.event.SEMEventUnit;
import com.bee32.sem.process.verify.builtin.AllowListPolicy;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.MultiLevelPolicy;
import com.bee32.sem.process.verify.builtin.PassStep;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;

@ImportUnit(SEMEventUnit.class)
public class SEMProcessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(VerifyPolicyPref.class);
        add(AllowListPolicy.class);
        add(MultiLevelPolicy.class, MultiLevel.class);
        add(PassToNextPolicy.class, PassStep.class);
    }

}
