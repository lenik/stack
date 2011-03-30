package com.bee32.icsf.access.acl;

import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

public abstract class AbstractACL
        implements IACL {

    @Override
    public Set<? extends IPrincipal> getDeclaredRelatedPrincipals() {
        Set<IPrincipal> declaredPrincipals;
        declaredPrincipals = new HashSet<IPrincipal>();
        for (Entry entry : getEntries()) {
            IPrincipal p = entry.getPrincipal();
            declaredPrincipals.add(p);
        }
        return declaredPrincipals;
    }

    @Override
    public final Set<? extends IPrincipal> getRelatedPrincipals() {
        Set<IPrincipal> all = new HashSet<IPrincipal>();
        IACL acl = this;
        while (acl != null) {
            all.addAll(acl.getDeclaredRelatedPrincipals());
            acl = getInheritedACL();
        }
        return all;
    }

    @Override
    public boolean isDeclaredRelated(IPrincipal principal) {
        for (Entry entry : getEntries()) {
            IPrincipal declaredPrincipal = entry.getPrincipal();
            if (declaredPrincipal.equals(principal))
                return true;
        }
        return false;
    }

    @Override
    public boolean isRelated(IPrincipal principal) {
        if (isDeclaredRelated(principal))
            return true;

        IACL inheritedACL = getInheritedACL();
        if (inheritedACL == null)
            return false;

        return inheritedACL.isRelated(principal);
    }

    @Override
    public final boolean isAllowed(Permission permission) {
        return getACLPolicy().isAllowed(this, permission);
    }

    @Override
    public final boolean isDenied(Permission permission) {
        return getACLPolicy().isDenied(this, permission);
    }

    protected abstract IACL newACLRange();

    @Override
    public IACL select(IPrincipal selectPrincipal) {
        IACL acl = newACLRange();
        for (Entry entry : getEntries()) {
            IPrincipal declaredPrincipal = entry.getPrincipal();
            if (selectPrincipal.implies(declaredPrincipal))
                acl.add(entry);
        }
        return acl;
    }

    @Override
    public IACL select(Permission selectPermission) {
        IACL acl = newACLRange();
        for (Entry entry : getEntries()) {
            Permission declaredPermission = entry.getPermission();
            if (declaredPermission.implies(selectPermission))
                acl.add(entry);
        }
        return acl;
    }

    @Override
    public IACL select(IPrincipal selectPrincipal, Permission selectPermission) {
        IACL acl = newACLRange();
        for (Entry entry : getEntries()) {
            IPrincipal declaredPrincipal = entry.getPrincipal();
            Permission declaredPermission = entry.getPermission();
            if (selectPrincipal.implies(declaredPrincipal) && declaredPermission.implies(selectPermission))
                acl.add(entry);
        }
        return acl;
    }

    @Override
    public int remove(IPrincipal principal, Permission permission) {
        int count = 0;
        for (Entry entry : this.select(principal, permission).getEntries())
            if (this.remove(entry))
                count++;
        return count;
    }

    @Override
    public int remove(IPrincipal principal) {
        int count = 0;
        for (Entry entry : this.select(principal).getEntries())
            if (this.remove(entry))
                count++;
        return count;

    }

    @Override
    public int remove(Permission permission) {
        int count = 0;
        for (Entry entry : this.select(permission).getEntries())
            if (this.remove(entry))
                count++;
        return count;
    }

}
