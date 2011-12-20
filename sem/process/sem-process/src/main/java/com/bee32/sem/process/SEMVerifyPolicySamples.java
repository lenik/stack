package com.bee32.sem.process;

import static com.bee32.icsf.principal.IcsfPrincipalSamples.alice;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.eva;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.kate;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.wallE;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMVerifyPolicySamples
        extends SampleContribution {

    public static final SingleVerifierPolicy robotList;
    public static final SingleVerifierPolicy plainList;
    public static final SingleVerifierPolicy kateWallE;

    public static final SingleVerifierRankedPolicy macLevel;

    static {
        robotList = new SingleVerifierPolicy(eva, wallE);
        robotList.setLabel("机器人");
        robotList.setDescription("机器人统治地球之权威机器人名单");

        plainList = new SingleVerifierPolicy(kate, alice, eva);
        plainList.setLabel("凯特的好友");
        plainList.setDescription("凯特以及她的好友。");

        kateWallE = new SingleVerifierPolicy(kate, wallE);
        kateWallE.setLabel("凯特和瓦力");
        kateWallE.setDescription("凯特和瓦力的双边信任关系");

        macLevel = new SingleVerifierRankedPolicy();
        macLevel.setLabel("Mac 分级策略");
        macLevel.setDescription("1千以内由凯特好友审批，1万以内由机器人审批。");
        macLevel.addLevel(1000, plainList);
        macLevel.addLevel(10000, robotList);
    }

    @Override
    protected void preamble() {
        add(robotList);
        add(plainList);
        add(kateWallE);
        add(macLevel);
    }

}
