package com.bee32.sems.security.access;

public class PermissionException
        extends AccessControlException {

    private static final long serialVersionUID = 1L;

    private final Permission requiredPermission;

    public PermissionException(Permission requiredPermission) {
        super();
        this.requiredPermission = requiredPermission;
    }

    public PermissionException(String message, Permission requiredPermission) {
        super(message);
        this.requiredPermission = requiredPermission;
    }

    public Permission getRequiredPermission() {
        return requiredPermission;
    }

}
