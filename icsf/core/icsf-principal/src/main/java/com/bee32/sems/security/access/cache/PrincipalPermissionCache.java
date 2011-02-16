package com.bee32.sems.security.access.cache;

import java.util.HashMap;
import java.util.Map;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.InvalidateEvent;
import com.bee32.sems.security.access.InvalidateListener;
import com.bee32.sems.security.access.Permission;

public class PrincipalPermissionCache
        implements InvalidateListener {

    private Map<IPrincipal, Permission> cache;

    public PrincipalPermissionCache() {
        cache = new HashMap<IPrincipal, Permission>();
    }

    public boolean hasPermission(IPrincipal principal, Permission permission) {
        return false;
    }

    @Override
    public void invalidate(InvalidateEvent event) {
        Object key = event.getKey();
        cache.remove(key);
    }

}
