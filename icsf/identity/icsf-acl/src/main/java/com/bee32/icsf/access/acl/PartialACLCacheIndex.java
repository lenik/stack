package com.bee32.icsf.access.acl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.DataService;

public class PartialACLCacheIndex
        extends DataService
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<Principal, PartialACLCache> map = new HashMap<Principal, PartialACLCache>();

    public PartialACLCache getACLCache(Principal principal) {
        PartialACLCache aclCache = map.get(principal);
        if (aclCache == null) {
            aclCache = new PartialACLCache();

            List<ACL> acls = ctx.data.access(ACLEntry.class).listMisc(//
                    ACLCriteria.acls(principal));
            Set<ACL> distincts = new HashSet<ACL>(acls);

            for (ACL acl : distincts)
                aclCache.addACL(acl, principal);

            map.put(principal, aclCache);
        }
        return aclCache;
    }

    public void invalidate(Principal principal) {
        map.remove(principal);
    }

    public void invalidate(ACL acl) {
        for (PartialACLCache aclCache : map.values())
            aclCache.removeACL(acl);
    }

}
