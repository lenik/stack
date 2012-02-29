package com.bee32.sem.process;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.plover.orm.util.SampleList;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;

public class SEMVerifyPolicySamples
        extends NormalSamples {

    public final SingleVerifierPolicy robotList = new SingleVerifierPolicy();
    public final SingleVerifierPolicy plainList = new SingleVerifierPolicy();
    public final SingleVerifierPolicy kateWallE = new SingleVerifierPolicy();
    public final SingleVerifierRankedPolicy macLevel = new SingleVerifierRankedPolicy();

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);

    @Override
    protected void wireUp() {
        robotList.setLabel("机器人");
        robotList.setDescription("机器人统治地球之权威机器人名单");
        robotList.addResponsible(principals.eva);
        robotList.addResponsible(principals.wallE);

        plainList.setLabel("凯特的好友");
        plainList.setDescription("凯特以及她的好友。");
        plainList.addResponsible(principals.kate);
        plainList.addResponsible(principals.alice);
        plainList.addResponsible(principals.eva);

        kateWallE.setLabel("凯特和瓦力");
        kateWallE.setDescription("凯特和瓦力的双边信任关系");
        kateWallE.addResponsible(principals.kate);
        kateWallE.addResponsible(principals.wallE);

        macLevel.setLabel("Mac 分级策略");
        macLevel.setDescription("1千以内由凯特好友审批，1万以内由机器人审批。");
        macLevel.addLevel(1000, plainList);
        macLevel.addLevel(10000, robotList);
    }

    @Override
    protected void getSamples(SampleList samples) {
        samples.add(robotList);
        samples.add(plainList);
        samples.add(kateWallE);
        samples.add(macLevel);
    }

}
