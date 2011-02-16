package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.PermissionException;

public interface IACLPolicy {

    void checkPermission(IACL acl, Permission requiredPermission)
            throws PermissionException;

}
