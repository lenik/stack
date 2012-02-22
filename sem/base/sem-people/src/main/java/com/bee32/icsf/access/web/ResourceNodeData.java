package com.bee32.icsf.access.web;

import java.io.Serializable;

import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;

public class ResourceNodeData
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String typeName;
    String qualifiedName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Resource getResource() {
        if (qualifiedName == null)
            return null;
        else
            return ResourceRegistry.query(qualifiedName);
    }

    public void setResource(Resource resource) {
        if (qualifiedName != null)
            qualifiedName = null;
        typeName = resource.getTypeName();
        qualifiedName = ResourceRegistry.qualify(resource);
    }

    public String getResourceLabel() {
        if (qualifiedName == null)
            return null;
        Resource resource = ResourceRegistry.query(qualifiedName);
        return resource.getAppearance().getDisplayName();
    }

    public String getResourceDescription() {
        if (qualifiedName == null)
            return null;
        Resource resource = ResourceRegistry.query(qualifiedName);
        return resource.getAppearance().getDescription();
    }

    @Override
    public String toString() {
        return qualifiedName;
    }

}
