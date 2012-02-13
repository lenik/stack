package com.bee32.icsf.access.alt;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;

@ImportSamples(IcsfPrincipalSamples.class)
public class R_ACLSamples
        extends SampleContribution {

    static R_ACE admin_all;
    static R_ACE service_tom_x;
    static R_ACE method1_kate_rx;
    static R_ACE foo_reguser_w;

    public R_ACLSamples() {
        admin_all = new R_ACE("ap:",//
                Role.adminRole, "s");

        service_tom_x = new R_ACE("ap:TestService.", //
                IcsfPrincipalSamples.tom, "x");

        method1_kate_rx = new R_ACE("ap:TestService.method1.", //
                IcsfPrincipalSamples.kate, "rx");

        foo_reguser_w = new R_ACE("ap:TestService.foo.", //
                Role.userRole, "w");
    }

    @Override
    protected void preamble() {
        add(admin_all);
        add(service_tom_x);
        add(method1_kate_rx);
        add(foo_reguser_w);
    }

}
