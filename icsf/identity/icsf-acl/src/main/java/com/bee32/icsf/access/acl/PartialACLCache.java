package com.bee32.icsf.access.acl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;

public class PartialACLCache
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<Integer, Permission> partialMergedMap = new HashMap<Integer, Permission>();
    Map<Permission, Set<Integer>> reversedMap = new HashMap<Permission, Set<Integer>>();

    /**
     * @return <code>null</code> if no ACL is declared for the partial-key (principal here) at all.
     */
    public Permission getPartialEffectivePermission(int aclId) {
        Permission merged = partialMergedMap.get(aclId);
        return merged;
    }

    public synchronized Set<Integer> getACLsImply(Permission permission) {
        Set<Integer> aclSet = reversedMap.get(permission);
        if (aclSet == null) {
            aclSet = new HashSet<Integer>();
            for (Entry<Integer, Permission> entry : partialMergedMap.entrySet()) {
                Permission merged = entry.getValue();
                if (merged.implies(permission)) {
                    aclSet.add(entry.getKey());
                }
            }
            reversedMap.put(permission, aclSet);
        }
        return aclSet;
    }

    public synchronized void addACL(ACL acl, Principal partialKeyPrincipal) {
        if (acl == null)
            throw new NullPointerException("acl");
        if (partialKeyPrincipal == null)
            throw new NullPointerException("partialKeyPrincipal");

        Permission merged = new Permission(0);
        for (ACLEntry entry : acl.getEntries()) {
            if (partialKeyPrincipal.equals(entry.getPrincipal())) {
                Permission p = entry.getPermission();
                merged.merge(p);
            }
        }
        partialMergedMap.put(acl.getId(), merged);

        // update related cache, but don't rebuild the whole reversed map.
        for (Entry<Permission, Set<Integer>> entry : reversedMap.entrySet()) {
            Permission p = entry.getKey();
            if (merged.implies(p)) {
                Set<Integer> aclSet = entry.getValue();
                aclSet.add(acl.getId());
            }
        }
    }

    public synchronized void removeACL(ACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");
        partialMergedMap.remove(acl.getId());
        for (Set<Integer> aclSet : reversedMap.values())
            aclSet.remove(acl.getId());
    }

    public synchronized void updateACL(ACL acl, Principal partialKeyPrincipal) {
        removeACL(acl);
        addACL(acl, partialKeyPrincipal);
    }

}
