package com.bee32.plover.orm.unit.xgraph;

import java.util.List;

import com.bee32.plover.orm.entity.Entity;

public class EntityPartialRefs {

    final EntityXrefMetadata metadata;
    final Class<? extends Entity<?>> clientType;
    final String label;
    final List<? extends Entity<?>> list;

    @SuppressWarnings("unchecked")
    public EntityPartialRefs(EntityXrefMetadata metadata, List<? extends Entity<?>> list) {
        if (metadata == null)
            throw new NullPointerException("xrefMetadata");
        this.metadata = metadata;
        this.clientType = (Class<? extends Entity<?>>) metadata.propertyClass;
        this.label = metadata.toString(); // property @ entity
        this.list = list;
    }

    public EntityXrefMetadata getMetadata() {
        return metadata;
    }

    public Class<? extends Entity<?>> getClientType() {
        return clientType;
    }

    public String getLabel() {
        return label;
    }

    public List<? extends Entity<?>> getList() {
        return list;
    }

}
