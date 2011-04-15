package com.bee32.sem.process;

import static com.bee32.icsf.principal.IcsfPrincipalSamples.alice;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.eva;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.kate;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.wallE;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.MultiLevel;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMProcessSamples
        extends EntitySamplesContribution {

    public static final AllowList robotList;
    public static final AllowList plainList;

    public static final MultiLevel macLevel;

    static {
        robotList = new AllowList(eva, wallE);
        plainList = new AllowList(kate, alice);

        macLevel = new MultiLevel();
        macLevel.addRange(1000, plainList);
        macLevel.addRange(10000, robotList);
    }

    @Override
    protected void preamble() {
        addNormalSample(robotList);
        addNormalSample(plainList);
        addNormalSample(macLevel);
    }

}
