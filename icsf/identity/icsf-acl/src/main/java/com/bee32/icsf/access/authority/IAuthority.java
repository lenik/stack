package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.IComponent;

public interface IAuthority
        extends IComponent {

    boolean trusts(IAuthority authority);

    IACL getACL(Resource resource);

    /**
     * Get the granted ACL for the principal.
     *
     * @param principal
     *            Non-<code>null</code> principal.
     * @return Returns non-<code>null</code> PrincipalACL for the resource.
     */
    Permission getPermission(Resource resource, IPrincipal principal);

    IAuthority ROOT = RootAuthority.getInstance();

}
