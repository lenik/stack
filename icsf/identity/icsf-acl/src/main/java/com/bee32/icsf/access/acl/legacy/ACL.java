package com.bee32.icsf.access.acl.legacy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.principal.IPrincipal;

public class ACL
        extends AbstractACL {

    private final IACL parent;
    private final Map<IPrincipal, Permission> map;

    public ACL() {
        this((IACL) null);
    }

    public ACL(IACL parent) {
        this.parent = parent;
        this.map = new HashMap<IPrincipal, Permission>();
    }

    public ACL(Map<IPrincipal, Permission> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.parent = null;
        this.map = map;
    }

    @Override
    public IACL getInheritedACL() {
        return parent;
    }

    @Override
    public Set<? extends IPrincipal> getDeclaredPrincipals() {
        return map.keySet();
    }

    @Override
    public Permission getDeclaredPermission(IPrincipal principal) {
        return map.get(principal);
    }

    @Override
    public Collection<? extends Entry<? extends IPrincipal, Permission>> getEntries() {
        return map.entrySet();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void add(Entry<? extends IPrincipal, Permission> entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        map.put(entry.getKey(), entry.getValue());
    }

    @Override
    public Permission add(IPrincipal principal, Permission permission) {
        Permission existing = map.get(principal);
        if (existing == null) {
            map.put(principal, permission);
            return permission;
        } else {
            existing.merge(permission);
            return existing;
        }
    }

    @Override
    public boolean remove(Entry<? extends IPrincipal, Permission> entry) {
        if (entry == null)
            return false;

        IPrincipal principal = entry.getKey();
        Permission existing = map.get(principal);
        if (existing != null) {
            if (existing.equals(entry.getValue())) {
                map.remove(principal);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(IPrincipal principal) {
        Permission existing = map.remove(principal);
        return existing != null;
    }

}
