package com.bee32.icsf.access.alt;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.DataService;

public class R_ACLService
        extends DataService {

    @Inject
    R_ACLDao r_ACLDao;

    public R_ACL loadACL(Resource resource) {
        return r_ACLDao.loadACL(resource);
    }

    @AccessCheck
    @Transactional(readOnly = false)
    public void saveACL(R_ACL acl) {
        r_ACLDao.saveACL(acl);
    }

    public List<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        return r_ACLDao.getResourcePermissions(principal);
    }

    /**
     * @return Non-<code>null</code> permission. If no permission was declared, a new empty
     *         permission is created.
     */
    public Permission getPermission(Resource resource, IPrincipal principal) {
        Permission permission = r_ACLDao.getPermission(resource, principal);
        if (permission == null)
            permission = new Permission(0);
        return permission;
    }

}
