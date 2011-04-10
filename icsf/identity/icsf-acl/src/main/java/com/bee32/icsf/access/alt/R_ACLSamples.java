package com.bee32.icsf.access.alt;

import static com.bee32.icsf.principal.IcsfPrincipalSamples.kate;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.registeredRole;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.tom;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;

@ImportSamples(IcsfPrincipalSamples.class)
public class R_ACLSamples
        extends EntitySamplesContribution {

    public R_ACE service_tom_x;
    public R_ACE method1_kate_rx;
    public R_ACE foo_reguser_w;

    public R_ACLSamples() {
        service_tom_x = new R_ACE("ap:TestService", tom, "x");
        method1_kate_rx = new R_ACE("ap:TestService.method1", kate, "rx");
        foo_reguser_w = new R_ACE("ap:TestService.foo", registeredRole, "w");
    }

    @Override
    protected void preamble() {
        addNormalSample(service_tom_x);
        addNormalSample(method1_kate_rx);
        addNormalSample(foo_reguser_w);
    }

}
