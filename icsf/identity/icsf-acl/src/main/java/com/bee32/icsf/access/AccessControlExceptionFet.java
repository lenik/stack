package com.bee32.icsf.access;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.faces.AbstractFet;

public class AccessControlExceptionFet
        extends AbstractFet {

    @Override
    public int handle(Throwable exception) {
        if (exception instanceof AccessControlException)
            return REPLACE;
        else
            return SKIP;
    }

    @Override
    public String translate(Throwable exception, String message) {
        AccessControlException e = (AccessControlException) exception;

        Class<?> resourceType = e.getResourceType();
        String resourceName = ClassUtil.getTypeName(resourceType);

        Permission requiredPermission = e.getRequiredPermission();
        String permissionName = requiredPermission.getReadableString();

        return "您没有" + permissionName + resourceName + "的权限。";
    }

}
