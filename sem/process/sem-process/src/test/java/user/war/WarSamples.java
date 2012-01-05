package user.war;

import java.util.Date;

import user.war.entity.AttackMission;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

@ImportSamples(SEMVerifyPolicySamples.class)
public class WarSamples
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
        SingleVerifierSupport killSimpons_SV = killSimpsons.getVerifyContext();
        killSimpons_SV.setAccepted1(false);
        killSimpons_SV.setVerifier1(IcsfPrincipalSamples.wallE);
        killSimpons_SV.setRejectedReason1("无差别拒绝");
        killSimpons_SV.setVerifiedDate1(new Date());

        rescueMao = new AttackMission();
        rescueMao.setTarget("联手灭绝师太打倒五毛党。");
        SingleVerifierSupport rescueMao_SV = rescueMao.getVerifyContext();
        rescueMao_SV.setAccepted1(true);
        rescueMao_SV.setVerifier1(IcsfPrincipalSamples.kate);
        rescueMao_SV.setVerifiedDate1(new Date());
    }

    @Override
    protected void preamble() {
        add(attackPref);
        add(bombAmerica);
        add(killSimpsons);
        add(rescueMao);
    }

}
