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
    protected void listSamples() {
        new UserPassword(principals.eva, digest("EVA"));
        new UserPassword(principals.wallE, digest("WALL-E"));
        new UserPassword(principals.alice, digest("ALICE"));
        new UserPassword(principals.tom, digest("TOM"));
        new UserPassword(principals.kate, digest("KATE"));
    }

}
