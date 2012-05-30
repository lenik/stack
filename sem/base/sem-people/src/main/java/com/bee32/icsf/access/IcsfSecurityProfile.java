package com.bee32.icsf.access;

import com.bee32.plover.arch.AppProfile;
import com.bee32.plover.ox1.principal.IcsfPrincipalMenu;

public class IcsfSecurityProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(IcsfPrincipalMenu.class, IcsfPrincipalMenu.ENABLED, true);
        setParameter(IcsfAccessMenu.class, IcsfAccessMenu.ENABLED, true);
    }

}
