package com.bee32.icsf.access.alt;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.plover.arch.DataService;

public class ResourceACLService
        extends DataService {

    @Inject
    ResourceACLDao resourceACLDao;

    public ResourceACL loadACL(Resource resource) {
        return resourceACLDao.loadACL(resource);
    }

    @AccessCheck
    @Transactional(readOnly = false)
    public void saveACL(ResourceACL acl) {
        resourceACLDao.saveACL(acl);
    }

    public List<ResourcePermission> getResourcePermissions(int principalId) {
        return resourceACLDao.getResourcePermissions(principalId);
    }

    /**
     * @return Non-<code>null</code> permission. If no permission was declared, a new empty
     *         permission is created.
     */
    public Permission getPermission(Resource resource, int principalId) {
        Permission permission = resourceACLDao.getPermission(resource, principalId);
        if (permission == null)
            permission = new Permission(0);
        return permission;
    }

}
