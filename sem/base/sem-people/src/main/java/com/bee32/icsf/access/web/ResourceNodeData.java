package com.bee32.icsf.access.web;

import java.io.Serializable;

import com.bee32.icsf.access.resource.Resource;

public class ResourceNodeData
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String typeName;
    Resource resource;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
        if (resource != null)
            typeName = resource.getTypeName();
    }

    public String getResourceLabel() {
        if (resource == null)
            return null;
        else
            return resource.getAppearance().getDisplayName();
    }

    public String getResourceDescription() {
        if (resource == null)
            return null;
        else
            return resource.getAppearance().getDescription();
    }

}
