package com.bee32.sem.process;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.process.verify.testbiz.AttackMission;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;

@ImportSamples(SEMVerifyPolicySamples.class)
public class SEMProcessTestSamples
        extends EntitySamplesContribution {

    public static VerifyPolicyPref attackPref;

    static {
        attackPref = new VerifyPolicyPref();
        attackPref.setType(AttackMission.class);
        attackPref.setPreferredPolicy(SEMVerifyPolicySamples.robotList);
        attackPref.setDescription("必须让机器人统治世界。");
    }

    @Override
    protected void preamble() {
        addNormalSample(attackPref);
    }

}
