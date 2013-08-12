package com.bee32.icsf.access;

import com.bee32.icsf.InternalSecurityException;

/**
 * 访问控制异常
 */
public class AccessControlException
        extends InternalSecurityException {

    private static final long serialVersionUID = 1L;

    Class<?> resourceType;
    Permission requiredPermission;

    public AccessControlException() {
        super();
    }

    public AccessControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessControlException(String message) {
        super(message);
    }

    public AccessControlException(Throwable cause) {
        super(cause);
    }

    public Class<?> getResourceType() {
        return resourceType;
    }

    public void setResourceType(Class<?> resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 需权限
     *
     * 所需要的权限。
     */
    public Permission getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(Permission requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    @Override
    public String getLocalizedMessage() {
        return "访问被禁止：" + getMessage();
    }

}
