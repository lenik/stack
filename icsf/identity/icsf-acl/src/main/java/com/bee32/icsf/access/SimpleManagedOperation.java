package com.bee32.icsf.access;

import java.io.Serializable;

public final class SimpleManagedOperation
        implements IManagedOperation, Serializable {

    private static final long serialVersionUID = 1L;

    private final int visibility;
    private final String name;
    private final String displayName;
    private final String description;
    private final Permission requiredPermission;

    public SimpleManagedOperation(String name, Permission requiredPermission) {
        this(PUBLIC, name, name, null, requiredPermission);
    }

    public SimpleManagedOperation(int visibility, String name, String displayName, String description,
            Permission requiredPermission) {
        if (name == null)
            throw new NullPointerException("name");
        if (displayName == null)
            throw new NullPointerException("displayName");
        this.visibility = visibility;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.requiredPermission = requiredPermission;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @Override
    public Permission getRequiredPermission() {
        return requiredPermission;
    }

    @Override
    public int hashCode() {
        int hash = 0xd8cd0264;
        hash += name.hashCode();
        hash <<= 3;
        hash += displayName.hashCode();
        hash <<= 3;
        // if (description != null) hash += description.hashCode();
        hash += visibility * 0xd8c43c71;
        if (requiredPermission != null)
            hash += requiredPermission.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof SimpleManagedOperation))
            return false;
        SimpleManagedOperation that = (SimpleManagedOperation) obj;
        if (!name.equals(that.name))
            return false;
        if (!displayName.equals(that.name))
            return false;
        if (requiredPermission != that.requiredPermission) {
            if (requiredPermission == null || that.requiredPermission == null)
                return false;
            if (!requiredPermission.equals(that.requiredPermission))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " requires " + requiredPermission;
    }

}
