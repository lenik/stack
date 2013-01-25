package com.bee32.plover.orm.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.IResourceScanner;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class EntityResourceNS
        implements IResourceNamespace, IResourceScanner {

    public static final String NS = "entity";

    boolean scanned;
    Map<String, EntityResource> resources;

    public EntityResourceNS() {
        resources = new HashMap<String, EntityResource>();
    }

    @Override
    public String getNamespace() {
        return NS;
    }

    @Override
    public Class<? extends Resource> getResourceType() {
        return EntityResource.class;
    }

    @Override
    public EntityResource getResource(String localName) {
        scan();
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

        PersistenceUnit unit = SiteSessionFactoryBean.getForceUnit();

        for (ClassCatalog catalog : unit.getAllDependencies()) {
            String superLocalName = EntityResource.getLocalName(catalog, null);
            EntityResource superResource = new EntityResource(catalog, null);
            resources.put(superLocalName, superResource);

            for (Class<?> entityClass : catalog.getLocalClasses()) {
                EntityResource entityResource = new EntityResource(catalog, entityClass);
                String localName = entityResource.getName();
                resources.put(localName, entityResource);
            }
        }
    }

}
