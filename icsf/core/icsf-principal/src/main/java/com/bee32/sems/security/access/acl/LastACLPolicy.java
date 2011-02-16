package com.bee32.sems.security.access.acl;

import com.bee32.sems.security.access.Permission;
import com.bee32.sems.security.access.PermissionException;
import com.bee32.sems.security.access.acl.IACL.Entry;


public class LastACLPolicy
        implements IACLPolicy {

    private final boolean defaultAllow;

    public LastACLPolicy(boolean defaultAllow) {
        this.defaultAllow = defaultAllow;
    }

    @Override
    public void checkPermission(IACL acl, Permission requiredPermission)
            throws PermissionException {
        if (acl == null)
            throw new NullPointerException("acl");
        if (requiredPermission == null)
            throw new NullPointerException("requiredPermission");

        Entry entry = searchLastACE(acl, requiredPermission);

        if (entry == null) {
            if (!defaultAllow)
                throw new PermissionException("Access denied by default", requiredPermission);
        } else {
            if (entry.isDenied())
                throw new PermissionException("Access denied: " + entry, requiredPermission);
        }
    }

    Entry searchLastACE(IACL acl, Permission requiredPermission) {
        Entry match = null;
        IACL parent = acl.getInheritedACL();
        if (parent != null)
            match = searchLastACE(parent, requiredPermission);
        // whether or n ot the permission is set in parent ACL, it's overrided in child.
        for (Entry ace : acl.getEntries()) {
            Permission permission = ace.getPermission();
            if (permission.implies(requiredPermission))
                match = ace; // don't break, continue to search the last entry
        }
        return match;
    }

    private static final LastACLPolicy instance = new LastACLPolicy(false);

    public static LastACLPolicy getInstance() {
        return instance;
    }

}
