package com.bee32.icsf.access.alt;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.acl.legacy.Authority;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.IdUtils;

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
    public Permission getPermission(Resource resource, Principal principal) {
        Set<Integer> imset = IdUtils.getIdSet(principal.getImSet());

        Permission permission = aclDao.getPermission(resource, imset);
        if (permission == null)
            return new Permission(0);
        else
            return permission;
    }

    @Override
    public Collection<ResourcePermission> getResourcePermissions(Principal principal) {
        Set<Integer> imset = IdUtils.getIdSet(principal.getImSet());
        return aclDao.getResourcePermissions(imset);
    }

}
