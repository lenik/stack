package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL.Entry;

public class AlwaysOverrideACLPolicy
        implements IACLPolicy {

    private final boolean defaultAllow;

    public AlwaysOverrideACLPolicy(boolean defaultAllow) {
        this.defaultAllow = defaultAllow;
    }

    @Override
    public boolean isAllowed(IACL acl, Permission permission) {
        if (acl == null)
            throw new NullPointerException("acl");
        if (permission == null)
            throw new NullPointerException("permission");

        Entry entry = searchLastACE(acl, permission);

        if (entry == null)
            return defaultAllow;
        else
            return entry.isAllowed();
    }

    @Override
    public boolean isDenied(IACL acl, Permission permission) {
        if (acl == null)
            throw new NullPointerException("acl");
        if (permission == null)
            throw new NullPointerException("permission");

        Entry entry = searchLastACE(acl, permission);

        if (entry == null)
            return !defaultAllow;
        else
            return entry.isDenied();
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

    private static final AlwaysOverrideACLPolicy instance = new AlwaysOverrideACLPolicy(false);

    public static AlwaysOverrideACLPolicy getInstance() {
        return instance;
    }

}
