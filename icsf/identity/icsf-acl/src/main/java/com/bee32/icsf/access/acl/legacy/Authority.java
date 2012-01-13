package com.bee32.icsf.access.acl.legacy;

import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.Component;

public abstract class Authority
        extends Component
        implements IAuthority {

    private Set<IAuthority> trustedAuthorities = new HashSet<IAuthority>();

    public Authority() {
        super();
    }

    public Authority(String name) {
        super(name);
    }

    @Override
    public boolean trusts(IAuthority authority) {
        return trustedAuthorities.contains(authority);
    }

    @Override
    public Permission getPermission(Resource resource, IPrincipal principal) {
        IACL acl = getACL(resource);
        if (acl == null)
            return null;
        return acl.getPermission(principal);
    }

}
