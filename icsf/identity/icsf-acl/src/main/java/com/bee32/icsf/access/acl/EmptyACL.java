package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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
    public Set<? extends IPrincipal> getDeclaredRelatedPrincipals() {
        return Collections.emptySet();
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return DenyFirstACLPolicy.getInstance();
    }

    @Override
    protected IACL newACLRange() {
        return this;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection<? extends Entry> getEntries() {
        return Collections.emptyList();
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
