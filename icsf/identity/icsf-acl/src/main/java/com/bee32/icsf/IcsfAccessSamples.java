package com.bee32.icsf;

import static com.bee32.icsf.login.UserPassword.digest;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.plover.orm.util.SampleList;

public class IcsfAccessSamples
        extends NormalSamples {

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);

    @Override
    protected void getSamples(SampleList samples) {
        new UserPassword(principals.eva, digest("EVA"));
        new UserPassword(principals.wallE, digest("WALL-E"));
        new UserPassword(principals.alice, digest("ALICE"));
        new UserPassword(principals.tom, digest("TOM"));
        new UserPassword(principals.kate, digest("KATE"));
    }

}
