package com.bee32.sem.people.web;

import java.io.Serializable;

import com.bee32.icsf.access.Permission;

public class RPEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String resourceType;
    String qualifiedName;
    String displayName;
    Permission permission;

    public String getKey() {
        return resourceType + qualifiedName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}
