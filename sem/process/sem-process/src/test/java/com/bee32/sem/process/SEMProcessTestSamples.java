package com.bee32.sem.process;

import java.util.Date;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.process.verify.testbiz.AttackMission;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;

@ImportSamples(SEMVerifyPolicySamples.class)
public class SEMProcessTestSamples
        extends EntitySamplesContribution {

    public static VerifyPolicyPref attackPref;

    public static AttackMission bombAmerica;
    public static AttackMission killSimpsons;
    public static AttackMission rescueMao;

    static {
        attackPref = new VerifyPolicyPref();
        attackPref.setType(AttackMission.class);
        attackPref.setPreferredPolicy(SEMVerifyPolicySamples.robotList);
        attackPref.setDescription("必须让机器人统治世界。");

        bombAmerica = new AttackMission();
        bombAmerica.setTarget("轰炸美国大本营");

        killSimpsons = new AttackMission();
        killSimpsons.setTarget("打击猖狂的辛普森一家，必须让巴特彻底崩溃。");
        killSimpsons.setAllowed(false);
        killSimpsons.setVerifier(IcsfPrincipalSamples.wallE);
        killSimpsons.setRejectReason("辛普森是好人，拒绝此项任务");
        killSimpsons.setVerifiedDate(new Date());

        rescueMao = new AttackMission();
        rescueMao.setTarget("拯救四面楚歌的五毛党，你们实在是太邪恶了！");
        rescueMao.setAllowed(true);
        rescueMao.setVerifier(IcsfPrincipalSamples.kate);
        rescueMao.setVerifiedDate(new Date());
    }

    @Override
    protected void preamble() {
        addNormalSample(attackPref);
        addNormalSample(bombAmerica);
        addNormalSample(killSimpsons);
        addNormalSample(rescueMao);
    }

}
