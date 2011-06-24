package com.bee32.plover.orm.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.IResourceScanner;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class EntityResourceNS
        implements IResourceNamespace, IResourceScanner {

    boolean scanned;
    Map<String, EntityResource> resources;

    public EntityResourceNS() {
        resources = new HashMap<String, EntityResource>();
    }

    @Override
    public String getNamespace() {
        return "entity";
    }

    @Override
    public Class<? extends Resource> getResourceType() {
        return EntityResource.class;
    }

    @Override
    public EntityResource getResource(String localName) {
        return resources.get(localName);
    }

    @Override
    public Collection<? extends EntityResource> getResources() {
        scan();
        return resources.values();
    }

    @Override
    public synchronized void scan() {
        if (scanned)
            return;
        scanned = true;

        // PersistenceUnit.getDefault().getClasses();
        PersistenceUnit unit = CustomizedSessionFactoryBean.getForceUnit();
        for (Class<?> entityClass : unit.getClasses()) {
            EntityResource entityResource = new EntityResource(entityClass);
            String localName = entityResource.getName();
            resources.put(localName, entityResource);
        }
    }

}
