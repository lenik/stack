package com.bee32.icsf.access.alt;

import javax.inject.Inject;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;

@ImportSamples(IcsfPrincipalSamples.class)
public class R_ACLSamples
        extends SampleContribution {

    final R_ACE admin_all = new R_ACE();
    final R_ACE service_tom_x = new R_ACE();
    final R_ACE method1_kate_rx = new R_ACE();
    final R_ACE foo_reguser_w = new R_ACE();

    @Inject
    IcsfPrincipalSamples principals;

    @Override
    public void preamble() {

        admin_all.init("ap:",//
                Role.adminRole, "s");

        service_tom_x.init("ap:TestService.", //
                principals.tom, "x");

        method1_kate_rx.init("ap:TestService.method1.", //
                principals.kate, "rx");

        foo_reguser_w.init("ap:TestService.foo.", //
                Role.userRole, "w");

        add(admin_all);
        add(service_tom_x);
        add(method1_kate_rx);
        add(foo_reguser_w);
    }

}
