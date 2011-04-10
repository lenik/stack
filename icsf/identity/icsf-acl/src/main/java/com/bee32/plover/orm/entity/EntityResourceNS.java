package com.bee32.plover.orm.entity;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;

public class EntityResourceNS
        implements IResourceNamespace {

    @Override
    public String getNamespace() {
        return "entity";
    }

    @Override
    public Class<? extends Resource> getResourceType() {
        return EntityResource.class;
    }

    @Override
    public Resource getResource(String localName) {
        return null;
    }

}
