package com.bee32.icsf;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.sample.NormalSamples;

public class IcsfAccessSamples
        extends NormalSamples {

    public final UserPassword eva = new UserPassword();
    public final UserPassword wallE = new UserPassword();
    public final UserPassword alice = new UserPassword();
    public final UserPassword tom = new UserPassword();
    public final UserPassword kate = new UserPassword();

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);

    @Override
    protected void wireUp() {
        eva.init(principals.eva, ("EVA"));
        wallE.init(principals.wallE, ("WALL-E"));
        alice.init(principals.alice, ("ALICE"));
        tom.init(principals.tom, ("TOM"));
        kate.init(principals.kate, ("KATE"));
    }

}
