package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.acl.EmptyACL;
import com.bee32.icsf.access.acl.PrincipalACL;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.IComponent;

public interface IAuthority
        extends IComponent {

    /**
     * Get the granted ACL for the principal.
     *
     * @param principal
     *            Non-<code>null</code> principal whose granted ACL is queried.
     * @return Non-<code>null</code> ACL granted to the principal. Returns {@link EmptyACL} if no
     *         available ACL for the principal.
     */
    PrincipalACL getGrantedACL(IPrincipal principal);

    boolean trusts(IAuthority authority);

    IAuthority ROOT = RootAuthority.getInstance();

}
