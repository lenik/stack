package com.bee32.sems.security.access.acl;

import com.bee32.sems.security.access.Permission;
import com.bee32.sems.security.access.PermissionException;


public interface IACLPolicy {

    void checkPermission(IACL acl, Permission requiredPermission)
            throws PermissionException;

}
