package com.bee32.icsf;

import static com.bee32.icsf.login.UserPassword.digest;

import javax.inject.Inject;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.SampleContribution;

public class IcsfAccessSamples
        extends SampleContribution {

    @Inject
    IcsfPrincipalSamples principals;

    @Override
    protected void preamble() {
        add(new UserPassword(principals.eva, digest("EVA")));
        add(new UserPassword(principals.wallE, digest("WALL-E")));
        add(new UserPassword(principals.alice, digest("ALICE")));
        add(new UserPassword(principals.tom, digest("TOM")));
        add(new UserPassword(principals.kate, digest("KATE")));
    }

}
