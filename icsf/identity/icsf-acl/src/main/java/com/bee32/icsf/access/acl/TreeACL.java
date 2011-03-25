package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.bee32.icsf.principal.IPrincipal;

/**
 *
 */
public class TreeACL
        extends AbstractACL {

    @Override
    public IACL getInheritedACL() {
        return null;
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return null;
    }

    @Override
    public Set<? extends IPrincipal> getDeclaredRelatedPrincipals() {
        return Collections.emptySet();
    }

    @Override
    protected IACL newACLRange() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection<? extends Entry> getEntries() {
        return null;
    }

    @Override
    public void add(Entry entry) {
    }

    @Override
    public boolean remove(Entry entry) {
        return false;
    }

}
