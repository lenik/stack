package com.bee32.icsf.access.alt;

import static com.bee32.icsf.principal.IcsfPrincipalSamples.kate;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;

@ImportSamples(IcsfPrincipalSamples.class)
public class R_ACLSamples
        extends EntitySamplesContribution {

    // public R_ACL kate_ACL;
    public R_ACE kate_CanRead;

    // public R_ACE kate_NoExecute;

    public R_ACLSamples() {
        // kate_ACL = new R_ACL();
        kate_CanRead = new R_ACE(null, kate, "r");
        // kate_NoExecute = kate_ACL.addDenyACE(kate, "x");
    }

    @Override
    protected void preamble() {
        addNormalSample(kate_CanRead);
    }

}
