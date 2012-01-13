package com.bee32.icsf.access.acl.legacy;

import java.util.Collection;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
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
     * @throws NullPointerException
     *             If <code>principal</code> is <code>null</code>.
     */
    Permission getPermission(Resource resource, IPrincipal principal);

    /**
     * Get resources declared for the principal.
     *
     * @param principal
     *            Non-<code>null</code> principal.
     * @return Returns non-<code>null</code> resource collection.
     * @throws NullPointerException
     *             If <code>principal</code> is <code>null</code>.
     */
    Collection<ResourcePermission> getResourcePermissions(IPrincipal principal);

    IAuthority ROOT = RootAuthority.getInstance();

}
