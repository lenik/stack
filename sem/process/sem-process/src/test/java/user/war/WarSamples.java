package user.war;

import java.util.Date;

import user.war.entity.AttackMission;
import user.war.entity.BuildMission;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

public class WarSamples
        extends NormalSamples {

    public VerifyPolicyPref attackPref = new VerifyPolicyPref();
    public AttackMission bombAmerica = new AttackMission();
    public AttackMission killSimpsons = new AttackMission();
    public BuildMission rescueMao = new BuildMission();

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);
    SEMVerifyPolicySamples policies = predefined(SEMVerifyPolicySamples.class);

    @Override
    protected void wireUp() {
        attackPref.setType(AttackMission.class);
        attackPref.setPreferredPolicy(policies.robotList);
        attackPref.setDescription(PREFIX + "必须让机器人统治世界。");

        bombAmerica.setTarget(PREFIX + "明日黄昏突袭美国东海岸。");

        killSimpsons.setTarget(PREFIX + "打击猖狂的辛普森一家，必须让巴特彻底崩溃。");
        SingleVerifierSupport killSimpons_SV = killSimpsons.getVerifyContext();
        killSimpons_SV.setAccepted1(false);
        killSimpons_SV.setVerifier1(principals.wallE);
        killSimpons_SV.setRejectedReason1("无差别拒绝");
        killSimpons_SV.setVerifiedDate1(new Date());

        rescueMao.setTarget(PREFIX + "拯救毛主席。");
        SingleVerifierSupport rescueMao_SV = rescueMao.getVerifyContext();
        rescueMao_SV.setAccepted1(true);
        rescueMao_SV.setVerifier1(principals.kate);
        rescueMao_SV.setVerifiedDate1(new Date());
    }

}
