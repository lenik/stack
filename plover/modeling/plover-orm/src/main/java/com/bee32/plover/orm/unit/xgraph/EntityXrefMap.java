package com.bee32.plover.orm.unit.xgraph;

import java.util.ArrayList;
import java.util.HashMap;

import com.bee32.plover.orm.entity.Entity;

public class EntityXrefMap
        extends HashMap<EntityXrefMetadata, EntityPartialRefs> {

    private static final long serialVersionUID = 1L;

    @Override
    public EntityPartialRefs get(Object key) {
        // if (key instanceof EntityXrefMetadata) {
        // EntityXrefMetadata metadata = (EntityXrefMetadata) key;
        // return getOrCreate(metadata);
        // }
        return super.get(key);
    }

    public synchronized EntityPartialRefs getOrCreate(EntityXrefMetadata xrefMetadata) {
        EntityPartialRefs list = super.get(xrefMetadata);
        if (list == null) {
            list = new EntityPartialRefs(xrefMetadata, new ArrayList<Entity<?>>());
            super.put(xrefMetadata, list);
        }
        return list;
    }

}
