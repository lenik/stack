package com.bee32.icsf.access.alt;

import static com.bee32.icsf.principal.test.PrincipalSamples.kate;

import com.bee32.icsf.access.builtins.GeneralPermission;
import com.bee32.icsf.principal.test.PrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;

@ImportSamples(PrincipalSamples.class)
public class R_ACLSamples
        extends EntitySamplesContribution {

    public R_ACE kate_READ;

    public R_ACLSamples() {
        kate_READ = new R_ACE(kate, GeneralPermission.READ, true);
    }

    @Override
    protected void preamble() {
        addNormalSample(kate_READ);
    }

}
