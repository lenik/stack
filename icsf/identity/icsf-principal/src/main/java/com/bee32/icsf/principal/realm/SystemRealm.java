package com.bee32.icsf.principal.realm;

import com.bee32.icsf.principal.IPrincipal;

public class SystemRealm
        extends AbstractRealm {

    @Override
    public boolean contains(IPrincipal principal) {
        return true;
    }

}
