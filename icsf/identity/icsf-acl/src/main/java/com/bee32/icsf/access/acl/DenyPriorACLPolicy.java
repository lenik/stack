package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.PermissionException;
import com.bee32.icsf.access.acl.IACL.Entry;

public class DenyPriorACLPolicy
        implements IACLPolicy {

    private final boolean defaultAllow;

    public DenyPriorACLPolicy(boolean defaultAllow) {
        this.defaultAllow = defaultAllow;
    }

    @Override
    public void checkPermission(IACL acl, Permission requiredPermission)
            throws PermissionException {
        if (acl == null)
            throw new NullPointerException("acl");
        if (requiredPermission == null)
            throw new NullPointerException("requiredPermission");

        boolean allowed = false;

        while (acl != null) {
            for (Entry e : acl.getEntries()) {
                Permission permission = e.getPermission();
                if (permission.implies(requiredPermission)) {
                    if (e.isDenied())
                        throw new PermissionException("Access denyed: " + e, requiredPermission);
                    if (e.isAllowed())
                        allowed = true;
                }
            }
            acl = acl.getInheritedACL();
        }

        if (!allowed)
            if (!defaultAllow)
                throw new PermissionException("Access denied by default", requiredPermission);
    }

    private static final DenyPriorACLPolicy instance = new DenyPriorACLPolicy(false);

    public static DenyPriorACLPolicy getInstance() {
        return instance;
    }

}
