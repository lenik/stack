package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.PermissionException;
import com.bee32.icsf.principal.IPrincipal;

public final class EmptyACL
        extends AbstractACL {

    EmptyACL() {
    }

    @Override
    public IACL getInheritedACL() {
        return null;
    }

    @Override
    public Collection<? extends IPrincipal> getDeclaredPrincipals() {
        return Collections.emptyList();
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return DenyPriorACLPolicy.getInstance();
    }

    @Override
    public void checkPermission(Permission requiredPermission)
            throws PermissionException {
        throw new PermissionException("EmptyACL", requiredPermission);
    }

    @Override
    public Collection<? extends Entry> getEntries() {
        return Collections.emptyList();
    }

    @Override
    protected IACL newACLRange() {
        return this;
    }

    @Override
    public void add(Entry entry) {
        throw new UnsupportedOperationException("Read-only ACL");
    }

    @Override
    public boolean remove(Entry entry) {
        return false;
    }

    static final EmptyACL instance = new EmptyACL();

    public static EmptyACL getInstance() {
        return instance;
    }

}
