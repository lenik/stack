package com.bee32.icsf.access.acl.legacy;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.Component;

public abstract class AbstractACL
        extends Component
        implements IACL {

    public AbstractACL() {
        super();
    }

    public AbstractACL(String name) {
        super(name);
    }

    @Override
    public IACL flatten() {
        IACL inheritedACL = getInheritedACL();
        if (inheritedACL == null)
            return this;

        Map<IPrincipal, Permission> all = new HashMap<IPrincipal, Permission>();
        merge(all, this);

        return new ACL(all);
    }

    static void merge(Map<IPrincipal, Permission> all, IACL acl) {
        for (Entry<? extends IPrincipal, Permission> entry : acl.getEntries()) {
            IPrincipal principal = entry.getKey();
            Permission permission = entry.getValue();

            Permission existing = all.get(principal);
            if (existing == null) {
                all.put(principal, permission);
            } else {
                existing.merge(permission);
            }
        }

        IACL inheritedACL = acl.getInheritedACL();
        if (inheritedACL == null)
            return;

        merge(all, inheritedACL);
    }

    @Override
    public final Set<? extends IPrincipal> getPrincipals() {
        Set<IPrincipal> all = new HashSet<IPrincipal>();
        IACL acl = this;
        while (acl != null) {
            all.addAll(acl.getDeclaredPrincipals());
            acl = getInheritedACL();
        }
        return all;
    }

    @Override
    public Permission getPermission(IPrincipal principal) {
        Permission permission = new Permission(0);

        for (Entry<? extends IPrincipal, Permission> entry : getEntries()) {
            IPrincipal declaredPrincipal = entry.getKey();
            Permission declaredPermission = entry.getValue();
            if (principal.implies(declaredPrincipal))
                permission.merge(declaredPermission);
        }

        return permission;
    }

    @Override
    public Collection<? extends IPrincipal> findPrincipals(Permission requiredPermission) {
        Set<IPrincipal> principals = new HashSet<IPrincipal>();
        for (Entry<? extends IPrincipal, Permission> entry : getEntries()) {
            Permission declaredPermission = entry.getValue();
            if (declaredPermission.implies(requiredPermission))
                principals.add(entry.getKey());
        }
        Collection<? extends IPrincipal> inherited = getInheritedACL().findPrincipals(requiredPermission);
        principals.addAll(inherited);
        return principals;
    }

    @Override
    public Collection<? extends IPrincipal> findPrincipals(String requiredMode) {
        return findPrincipals(new Permission(requiredMode));
    }

    @Override
    public Permission add(IPrincipal principal, String mode) {
        Permission permission = new Permission(mode);
        return add(principal, permission);
    }

    @Override
    public boolean remove(Entry<? extends IPrincipal, Permission> entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        IPrincipal principal = entry.getKey();
        return remove(principal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ACL(" + getName() + ")");

        for (Entry<? extends IPrincipal, Permission> entry : getEntries()) {
            IPrincipal principal = entry.getKey();
            Permission permission = entry.getValue();
            sb.append("\n    " + principal.getName() + " +" + permission);
        }

        return sb.toString();
    }

}
