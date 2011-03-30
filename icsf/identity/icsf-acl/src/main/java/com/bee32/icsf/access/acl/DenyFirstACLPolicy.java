package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
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
    public boolean isAllowed(IACL acl, Permission permission) {
        if (acl == null)
            throw new NullPointerException("acl");
        if (permission == null)
            throw new NullPointerException("permission");

        boolean allowed = false;

        while (acl != null) {
            for (Entry ace : acl.getEntries()) {
                Permission p = ace.getPermission();
                if (p.implies(permission)) {
                    if (ace.isDenied())
                        return false;
                    if (ace.isAllowed())
                        allowed = true;
                }
            }
            acl = acl.getInheritedACL();
        }

        return allowed || defaultAllow;
    }

    @Override
    public boolean isDenied(IACL acl, Permission permission) {
        return !isAllowed(acl, permission);
    }

    private static final DenyFirstACLPolicy instance = new DenyFirstACLPolicy(false);

    public static DenyFirstACLPolicy getInstance() {
        return instance;
    }

}
