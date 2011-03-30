package com.bee32.icsf.access.builtins;

import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.access.Permission;

public class GeneralPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    private Set<Permission> impliedPermissions;

    public GeneralPermission(String name, Permission... impliedPermissions) {
        super(name);

        if (impliedPermissions != null && impliedPermissions.length != 0) {
            this.impliedPermissions = new HashSet<Permission>();
            for (Permission p : impliedPermissions)
                this.impliedPermissions.add(p);
        }
    }

    @Override
    public boolean implies(Permission permission) {
        if (this == permission)
            return true;

        if (impliedPermissions != null)
            if (impliedPermissions.contains(permission))
                return true;

        return false;
    }

    public static final GeneralPermission ADMIN = new GeneralPermission("admin") {

        private static final long serialVersionUID = 1L;

        public boolean implies(Permission permission) {
            return true;
        }
    };

    public static final GeneralPermission REVOKE = new GeneralPermission("revoke");
    public static final GeneralPermission GRANT = new GeneralPermission("grant", REVOKE);

    public static final GeneralPermission DELETE = new GeneralPermission("delete");

    public static final GeneralPermission LIST = new GeneralPermission("list");

    public static final GeneralPermission READ = new GeneralPermission("read", LIST);
    public static final GeneralPermission WRITE = new GeneralPermission("write", READ, DELETE);

    public static final GeneralPermission EXECUTE = new GeneralPermission("execute", READ);

}
