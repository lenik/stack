package com.bee32.icsf.access.cache;

import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.InvalidateEvent;
import com.bee32.icsf.principal.InvalidateListener;
import com.bee32.plover.ox1.principal.IPrincipal;

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
