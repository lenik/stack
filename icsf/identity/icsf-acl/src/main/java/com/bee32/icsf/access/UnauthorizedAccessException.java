package com.bee32.icsf.access;

import com.bee32.plover.arch.util.ClassUtil;

/**
 * 未授权的访问异常
 *
 * 访问所需要的权限未被满足。
 */
public class UnauthorizedAccessException
        extends AccessControlException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedAccessException(String message, Class<?> resourceType, Permission requiredPermission) {
        super(message);
        setResourceType(resourceType);
        setRequiredPermission(requiredPermission);
    }

    @Override
    public String getLocalizedMessage() {
        Class<?> resourceType = getResourceType();
        String resourceName = ClassUtil.getTypeName(resourceType);

        Permission requiredPermission = getRequiredPermission();
        String permissionName = requiredPermission.getReadableString();

        return "您没有" + permissionName + resourceName + "的权限。";
    }

}
