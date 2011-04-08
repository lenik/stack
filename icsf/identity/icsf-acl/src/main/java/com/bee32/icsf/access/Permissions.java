package com.bee32.icsf.access;

import java.util.Map;
import java.util.TreeMap;

import javax.free.IllegalUsageException;

import com.bee32.icsf.access.resource.AccessPoint;

public class Permissions {

    private static Map<String, Permission> registry = new TreeMap<String, Permission>();

    public static synchronized void register(Permission permission) {
        String name = permission.getName();
        Permission existing = registry.get(name);
        if (existing != null)
            throw new IllegalStateException("Permission name " + name + " is already registered for " + existing);
        registry.put(name, permission);
    }

    public static Permission unregister(String permissionName) {
        return registry.remove(permissionName);
    }

    /**
     * Find permission by name.
     *
     * @param permissionName
     *            The permission name.
     * @return Non-<code>null</code> permission instance.
     * @throws IllegalUsageException
     *             If the permission name is undefined.
     * @see AccessPoint#create(String)
     */
    public static Permission getPermission(String permissionName) {
        if (permissionName == null)
            throw new NullPointerException("permissionName");

        Permission permission = registry.get(permissionName);

        if (permission == null)
            throw new IllegalUsageException("Not defined permission: " + permissionName);

        return permission;
    }

}
