package user.war;

import java.util.Date;

import javax.inject.Inject;

import user.war.entity.AttackMission;
import user.war.entity.BuildMission;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

@ImportSamples(SEMVerifyPolicySamples.class)
public class WarSamples
        extends SampleContribution {

    public final VerifyPolicyPref attackPref = new VerifyPolicyPref();

    public final AttackMission bombAmerica = new AttackMission();
    public final AttackMission killSimpsons = new AttackMission();
    public final BuildMission rescueMao = new BuildMission();

    @Inject
    IcsfPrincipalSamples principals;
    @Inject
    SEMVerifyPolicySamples policies;

    @Override
    protected void preamble() {
        attackPref.setType(AttackMission.class);
        attackPref.setPreferredPolicy(policies.robotList);
        attackPref.setDescription("必须让机器人统治世界。");

        bombAmerica.setTarget("明日黄昏突袭美国东海岸。");

        killSimpsons.setTarget("打击猖狂的辛普森一家，必须让巴特彻底崩溃。");
        SingleVerifierSupport killSimpons_SV = killSimpsons.getVerifyContext();
        killSimpons_SV.setAccepted1(false);
        killSimpons_SV.setVerifier1(principals.wallE);
        killSimpons_SV.setRejectedReason1("无差别拒绝");
        killSimpons_SV.setVerifiedDate1(new Date());

        rescueMao.setTarget("拯救毛主席。");
        SingleVerifierSupport rescueMao_SV = rescueMao.getVerifyContext();
        rescueMao_SV.setAccepted1(true);
        rescueMao_SV.setVerifier1(principals.kate);
        rescueMao_SV.setVerifiedDate1(new Date());

        add(attackPref);
        add(bombAmerica);
        add(killSimpsons);
        add(rescueMao);
    }

}
