package com.bee32.icsf.access.cache;

import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IPrincipalChangeListener;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalChangeEvent;

/**
 * 主体权限缓存
 */
public class PrincipalPermissionCache
        implements IPrincipalChangeListener {

    private Map<IPrincipal, Permission> principalPermissionMap;

    public PrincipalPermissionCache() {
        principalPermissionMap = new HashMap<IPrincipal, Permission>();
    }

    public boolean hasPermission(IPrincipal principal, Permission permission) {
        return false;
    }

    @Override
    public void principalChanged(PrincipalChangeEvent event) {
        Principal principal = event.getPrincipal();
        principalPermissionMap.remove(principal);
    }

}
