package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.acl.PrincipalACL;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.icsf.principal.IPrincipal;

public class RootAuthority
        extends Authority {

    RootAuthority() {
        super("Root");
    }

    @Override
    public PrincipalACL getGrantedACL(IPrincipal principal) {
        return null;
    }

    @Override
    public boolean trusts(IAuthority authority) {
        if (authority == null)
            throw new NullPointerException("authority");

        if (authority instanceof R_ACLDao)
            return true;

        return false;
    }

    private static final RootAuthority instance = new RootAuthority();

    public static RootAuthority getInstance() {
        return instance;
    }

}
