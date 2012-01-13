package com.bee32.icsf.access.acl.legacy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.principal.Principal;

public class ACL
        extends AbstractACL {

    private final IACL parent;
    private final Map<Principal, Permission> map;

    public ACL() {
        this((IACL) null);
    }

    public ACL(IACL parent) {
        this.parent = parent;
        this.map = new HashMap<Principal, Permission>();
    }

    public ACL(Map<Principal, Permission> map) {
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
    public Set<? extends Principal> getDeclaredPrincipals() {
        return map.keySet();
    }

    @Override
    public Permission getDeclaredPermission(Principal principal) {
        return map.get(principal);
    }

    @Override
    public Collection<? extends Entry<? extends Principal, Permission>> getEntries() {
        return map.entrySet();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void add(Entry<? extends Principal, Permission> entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        map.put(entry.getKey(), entry.getValue());
    }

    @Override
    public Permission add(Principal principal, Permission permission) {
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
    public boolean remove(Entry<? extends Principal, Permission> entry) {
        if (entry == null)
            return false;

        Principal principal = entry.getKey();
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
    public boolean remove(Principal principal) {
        Permission existing = map.remove(principal);
        return existing != null;
    }

}
