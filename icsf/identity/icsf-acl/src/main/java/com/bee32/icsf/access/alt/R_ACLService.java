package com.bee32.icsf.access.alt;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourcePermission;
import com.bee32.plover.arch.DataService;

/**
 * 权限设置服务
 */
public class R_ACLService
        extends DataService {

    @Inject
    R_ACLDao resourceACLDao;

    /**
     * 读取权限设置
     */
    public R_ACL loadACL(Resource resource) {
        return resourceACLDao.loadACL(resource);
    }

    /**
     * 保存权限设置
     */
    @AccessCheck
    @Transactional(readOnly = false)
    public void saveACL(R_ACL acl) {
        resourceACLDao.saveACL(acl);
    }

    public List<ResourcePermission> getResourcePermissions(Set<Integer> imset) {
        return resourceACLDao.getResourcePermissions(imset);
    }

    /**
     * @return Non-<code>null</code> permission. If no permission was declared, a new empty
     *         permission is created.
     */
    public Permission getPermission(Resource resource, Set<Integer> imset) {
        Permission permission = resourceACLDao.getPermission(resource, imset);
        return permission;
    }

}
