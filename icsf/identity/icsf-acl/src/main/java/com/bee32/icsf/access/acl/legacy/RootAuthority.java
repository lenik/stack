package com.bee32.icsf.access.acl.legacy;

import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.access.acl.EmptyACL;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.alt.R_Authority;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.icsf.principal.IPrincipal;

public class RootAuthority
        extends Authority {

    RootAuthority() {
        super("Root");
    }

    @Override
    public boolean trusts(IAuthority authority) {
        if (authority == null)
            throw new NullPointerException("authority");

        if (authority instanceof R_Authority)
            return true;

        return false;
    }

    @Override
    public IACL getACL(Resource resource) {
        return EmptyACL.getInstance();
    }

    @Override
    public Collection<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        return Collections.emptySet();
    }

    private static final RootAuthority instance = new RootAuthority();

    public static RootAuthority getInstance() {
        return instance;
    }

}
