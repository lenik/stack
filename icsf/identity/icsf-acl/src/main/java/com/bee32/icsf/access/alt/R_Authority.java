package com.bee32.icsf.access.alt;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.authority.Authority;
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

    @Override
    public Permission getPermission(Resource resource, IPrincipal principal) {
        return aclDao.getPermission(resource, principal);
    }

}
