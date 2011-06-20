package com.bee32.plover.orm.entity;

import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.orm.unit.PersistenceUnit;

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

    @Override
    public Collection<? extends Resource> getResources() {
        // XXX TSFB -> default?
        PersistenceUnit.getDefault().getClasses();
        return Collections.emptyList();
    }

}
