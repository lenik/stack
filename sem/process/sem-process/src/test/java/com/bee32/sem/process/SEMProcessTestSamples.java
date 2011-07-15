package com.bee32.sem.process;

import java.util.Date;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.process.verify.testbiz.AttackMission;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;

@ImportSamples(SEMVerifyPolicySamples.class)
public class SEMProcessTestSamples
        extends SampleContribution {

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
        bombAmerica.setTarget("明日黄昏突袭美国东海岸。");

        killSimpsons = new AttackMission();
        killSimpsons.setTarget("打击猖狂的辛普森一家，必须让巴特彻底崩溃。");
        killSimpsons.setAllowed(false);
        killSimpsons.setVerifier(IcsfPrincipalSamples.wallE);
        killSimpsons.setRejectedReason("无差别拒绝");
        killSimpsons.setVerifiedDate(new Date());

        rescueMao = new AttackMission();
        rescueMao.setTarget("联手灭绝师太打倒五毛党。");
        rescueMao.setAllowed(true);
        rescueMao.setVerifier(IcsfPrincipalSamples.kate);
        rescueMao.setVerifiedDate(new Date());
    }

    @Override
    protected void preamble() {
        add(attackPref);
        add(bombAmerica);
        add(killSimpsons);
        add(rescueMao);
    }

}
