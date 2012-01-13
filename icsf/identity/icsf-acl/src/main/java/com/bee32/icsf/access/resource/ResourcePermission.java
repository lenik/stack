package com.bee32.icsf.access.resource;

import com.bee32.icsf.access.Permission;

public class ResourcePermission {

    private Resource resource;
    private Permission permission;

    public ResourcePermission(Resource resource, Permission permission) {
        this.resource = resource;
        this.permission = permission;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}
