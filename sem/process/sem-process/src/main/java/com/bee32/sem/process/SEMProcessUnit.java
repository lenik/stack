package com.bee32.sem.process;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.event.SEMEventUnit;
import com.bee32.sem.process.verify.builtin.PassStep;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierLevel;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

@ImportUnit(SEMEventUnit.class)
public class SEMProcessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(VerifyPolicyPref.class);
        add(SingleVerifierPolicy.class);
        add(SingleVerifierRankedPolicy.class, SingleVerifierLevel.class);
        add(PassToNextPolicy.class, PassStep.class);
    }

}
