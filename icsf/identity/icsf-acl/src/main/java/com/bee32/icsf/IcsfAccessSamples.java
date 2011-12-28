package com.bee32.icsf;

import static com.bee32.icsf.login.UserPassword.digest;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.alice;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.eva;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.kate;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.tom;
import static com.bee32.icsf.principal.IcsfPrincipalSamples.wallE;

import com.bee32.icsf.login.UserPassword;
import com.bee32.plover.orm.util.SampleContribution;

public class IcsfAccessSamples
        extends SampleContribution {

    @Override
    protected void preamble() {
        add(new UserPassword(eva, digest("EVA")));
        add(new UserPassword(wallE, digest("WALL-E")));
        add(new UserPassword(alice, digest("ALICE")));
        add(new UserPassword(tom, digest("TOM")));
        add(new UserPassword(kate, digest("KATE")));
    }

}
