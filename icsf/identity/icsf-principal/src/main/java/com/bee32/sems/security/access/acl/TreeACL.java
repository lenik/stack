package com.bee32.sems.security.access.acl;

import java.util.Collection;

import com.bee32.sems.security.access.IPrincipal;

/**
 *
 */
public class TreeACL
        extends AbstractACL {

    @Override
    protected IACL newACLRange() {
        return null;
    }

    @Override
    public void add(Entry entry) {
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return null;
    }

    @Override
    public Collection<? extends IPrincipal> getDeclaredPrincipals() {
        return null;
    }

    @Override
    public Collection<? extends Entry> getEntries() {
        return null;
    }

    @Override
    public IACL getInheritedACL() {
        return null;
    }

    @Override
    public boolean remove(Entry entry) {
        return false;
    }

}
