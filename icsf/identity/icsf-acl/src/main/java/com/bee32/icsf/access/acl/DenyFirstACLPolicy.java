package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.PermissionException;
import com.bee32.icsf.access.acl.IACL.Entry;

/**
 * If both allow-ACE and deny-ACE are defined, the denied one take the precedence.
 * <p>
 * This should be the default ACL policy.
 */
public class DenyFirstACLPolicy
        implements IACLPolicy {

    private final boolean defaultAllow;

    public DenyFirstACLPolicy(boolean defaultAllow) {
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

    private static final DenyFirstACLPolicy instance = new DenyFirstACLPolicy(false);

    public static DenyFirstACLPolicy getInstance() {
        return instance;
    }

}
