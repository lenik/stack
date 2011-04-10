package com.bee32.icsf.access.alt;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.icsf.access.Authority;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.principal.IPrincipal;

@Lazy
@Service
public class R_Authority
        extends Authority {

    @Inject
    R_ACLDao aclDao;

    @Override
    public IACL getACL(Resource resource) {
        return aclDao.loadACL(resource);
    }

    /**
     * @return Non-<code>null</code> permission.
     */
    @Override
    public Permission getPermission(Resource resource, IPrincipal principal) {
        Permission permission = aclDao.getPermission(resource, principal);

        if (permission == null)
            permission = new Permission(0);

        return permission;
    }

    @Override
    public Collection<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        return aclDao.getResourcePermissions(principal);
    }

}
