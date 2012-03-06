package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

@MappedSuperclass
public abstract class AbstractACL<self_t extends AbstractACL<self_t>>
        extends TreeEntityAuto<Integer, self_t>
        implements IACL {

    private static final long serialVersionUID = 1L;

    public AbstractACL() {
        super();
    }

    public AbstractACL(self_t parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof AbstractACL)
            _populate((AbstractACL<self_t>) source);
        else
            super.populate(source);
    }

    protected void _populate(AbstractACL<self_t> o) {
        super._populate(o);
    }

    /**
     * Used to generate a flatten ACL.
     */
    protected abstract self_t create(Map<Principal, Permission> map);

    @Override
    public self_t flatten() {
        IACL inheritedACL = getInheritedACL();
        if (inheritedACL == null) {
            @SuppressWarnings("unchecked")
            self_t self = (self_t) this;
            return self;
        }

        Map<Principal, Permission> all = new HashMap<Principal, Permission>();
        merge(all, this);

        return create(all);
    }

    static void merge(Map<Principal, Permission> all, IACL acl) {
        for (Entry<? extends Principal, Permission> entry : acl.getDeclaredEntries().entrySet()) {
            Principal principal = entry.getKey();
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

    @Transient
    @Override
    public final Set<? extends Principal> getEffectivePrincipals() {
        Set<Principal> all = new HashSet<Principal>();
        IACL acl = this;
        while (acl != null) {
            all.addAll(acl.getDeclaredPrincipals());
            acl = getInheritedACL();
        }
        return all;
    }

    @Override
    public Permission getEffectivePermission(Principal principal) {
        Permission permission = new Permission(0);

        for (Entry<? extends Principal, Permission> entry : getDeclaredEntries().entrySet()) {
            Principal declaredPrincipal = entry.getKey();
            Permission declaredPermission = entry.getValue();
            if (principal.implies(declaredPrincipal))
                permission.merge(declaredPermission);
        }

        return permission;
    }

    @Override
    public Collection<? extends Principal> findPrincipals(Permission requiredPermission) {
        Set<Principal> principals = new HashSet<Principal>();
        for (Entry<? extends Principal, Permission> entry : getDeclaredEntries().entrySet()) {
            Permission declaredPermission = entry.getValue();
            if (declaredPermission.implies(requiredPermission))
                principals.add(entry.getKey());
        }
        Collection<? extends Principal> inherited = getInheritedACL().findPrincipals(requiredPermission);
        principals.addAll(inherited);
        return principals;
    }

    @Override
    public Collection<? extends Principal> findPrincipals(String requiredMode) {
        return findPrincipals(new Permission(requiredMode));
    }

    @Override
    public Permission add(Principal principal, String mode) {
        Permission permission = new Permission(mode);
        return add(principal, permission);
    }

    @Override
    public boolean remove(Entry<? extends Principal, Permission> entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        Principal principal = entry.getKey();
        return remove(principal);
    }

    @Transient
    public String getScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("ACL(" + getName() + ")");
        for (Entry<? extends Principal, Permission> entry : getDeclaredEntries().entrySet()) {
            Principal principal = entry.getKey();
            Permission permission = entry.getValue();
            sb.append("\n    " + principal.getName() + " +" + permission);
        }
        String text = sb.toString();
        return text;
    }

}
