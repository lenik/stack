package com.bee32.icsf.access.alt;

import static com.bee32.icsf.principal.test.PrincipalSamples.kate;

import com.bee32.icsf.access.builtins.GeneralPermission;
import com.bee32.icsf.principal.test.PrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;

@ImportSamples(PrincipalSamples.class)
public class R_ACLSamples
        extends EntitySamplesContribution {

    public R_ACL kate_ACL;
    public R_ACE kate_CanRead;
    public R_ACE kate_NoExecute;

    public R_ACLSamples() {
        kate_ACL = new R_ACL();
        kate_CanRead = kate_ACL.addAllowACE(kate, GeneralPermission.READ);
        kate_NoExecute = kate_ACL.addDenyACE(kate, GeneralPermission.EXECUTE);
    }

    @Override
    protected void preamble() {
        addNormalSample(kate_ACL);
    }

}
