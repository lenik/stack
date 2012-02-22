package com.bee32.icsf.access;


public class UnauthorizedAccessException
        extends AccessControlException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedAccessException(String message, Class<?> resourceType, Permission requiredPermission) {
        super(message);
        setResourceType(resourceType);
        setRequiredPermission(requiredPermission);
    }

}
