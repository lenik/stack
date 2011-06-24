package com.bee32.icsf.access.alt;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.EnterpriseService;

public class R_ACLService
        extends EnterpriseService {

    @Inject
    R_ACLDao r_ACLDao;

    @AccessCheck
    public R_ACL loadACL(Resource resource) {
        return r_ACLDao.loadACL(resource);
    }

    @AccessCheck
    @Transactional(readOnly = false)
    public void saveACL(R_ACL acl) {
        r_ACLDao.saveACL(acl);
    }

    @AccessCheck
    public List<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        return r_ACLDao.getResourcePermissions(principal);
    }

    @AccessCheck
    public Permission getPermission(Resource resource, IPrincipal principal) {
        return r_ACLDao.getPermission(resource, principal);
    }

}
