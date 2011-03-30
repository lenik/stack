package com.bee32.icsf.access.alt;

import com.bee32.icsf.access.builtins.GeneralPermission;
import com.bee32.icsf.principal.test.PrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;

public class R_ACLSamples
        extends EntitySamplesContribution {

    PrincipalSamples principals = PrincipalSamples.getInstance();

    public R_ACE kate_READ;

    public R_ACLSamples() {
        kate_READ = new R_ACE(principals.kate, GeneralPermission.READ, true);
    }

    @Override
    protected void preamble() {
        addNormalSample(kate_READ);
    }

}
