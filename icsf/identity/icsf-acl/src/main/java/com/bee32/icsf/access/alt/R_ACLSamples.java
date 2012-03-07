package com.bee32.icsf.access.alt;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.sample.NormalSamples;

public class R_ACLSamples
        extends NormalSamples {

    public final R_ACE admin_all = new R_ACE();
    public final R_ACE service_tom_x = new R_ACE();
    public final R_ACE method1_kate_rx = new R_ACE();
    public final R_ACE foo_reguser_w = new R_ACE();

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);
    Users users = predefined(Users.class);

    @Override
    public void wireUp() {
        admin_all.init("ap:", users.adminRole, "s");
        service_tom_x.init("ap:TestService.", principals.tom, "x");
        method1_kate_rx.init("ap:TestService.method1.", principals.kate, "rx");
        foo_reguser_w.init("ap:TestService.foo.", users.userRole, "w");
    }

}
