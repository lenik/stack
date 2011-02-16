package com.bee32.sems.security.access.acl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.Permission;
import com.bee32.sems.security.access.PermissionException;
import com.bee32.sems.security.access.PrincipalCheckException;

public abstract class AbstractACL
        implements IACL {

    public Collection<? extends IPrincipal> getPrincipals() {
        Set<IPrincipal> all = new HashSet<IPrincipal>();
        IACL acl = this;
        while (acl != null) {
            all.addAll(acl.getDeclaredPrincipals());
            acl = getInheritedACL();
        }
        return all;
    }

    void getImplicationACL(Collection<IPrincipal> all, IPrincipal principal)
            throws PrincipalCheckException {
        if (principal == null)
            throw new NullPointerException("principal");
        IACL inherited = getInheritedACL();
        if (inherited != null) {
            for (IPrincipal inherit : inherited.getDeclaredPrincipals()) {
                if (!all.add(inherit)) {
                    throw new PrincipalCheckException();
                }
            }
        }
    }

    @Override
    public void checkPermission(Permission requiredPermission)
            throws PermissionException {
        getACLPolicy().checkPermission(this, requiredPermission);
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
