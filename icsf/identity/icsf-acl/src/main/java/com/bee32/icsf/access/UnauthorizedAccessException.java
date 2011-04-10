package com.bee32.icsf.access;

public class UnauthorizedAccessException
        extends AccessControlException {

    private static final long serialVersionUID = 1L;

    private final Permission requiredPermission;

    public UnauthorizedAccessException(Permission requiredPermission) {
        super();
        this.requiredPermission = requiredPermission;
    }

    public UnauthorizedAccessException(String message, Permission requiredPermission) {
        super(message);
        this.requiredPermission = requiredPermission;
    }

    public Permission getRequiredPermission() {
        return requiredPermission;
    }

}
