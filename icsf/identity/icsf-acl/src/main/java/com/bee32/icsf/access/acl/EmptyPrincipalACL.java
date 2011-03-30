package com.bee32.icsf.access.acl;

import com.bee32.icsf.principal.IPrincipal;

public class EmptyPrincipalACL
        extends EmptyACL
        implements IPrincipalACL {

    private final IPrincipal principal;

    public EmptyPrincipalACL(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    @Override
    public IPrincipal getPrincipal() {
        return principal;
    }

}
