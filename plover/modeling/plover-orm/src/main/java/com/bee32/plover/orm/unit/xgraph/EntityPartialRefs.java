package com.bee32.plover.orm.unit.xgraph;

import java.util.List;

import com.bee32.plover.orm.entity.Entity;

public class EntityPartialRefs {

    final EntityXrefMetadata xrefMetadata;
    final Class<? extends Entity<?>> entityType;
    final String entityLabel;
    final String label;
    final List<? extends Entity<?>> list;

    @SuppressWarnings("unchecked")
    public EntityPartialRefs(EntityXrefMetadata xrefMetadata, List<? extends Entity<?>> list) {
        if (xrefMetadata == null)
            throw new NullPointerException("xrefMetadata");
        this.xrefMetadata = xrefMetadata;
        this.entityType = (Class<? extends Entity<?>>) xrefMetadata.propertyClass;
        this.entityLabel = xrefMetadata.entityLabel;
        this.label = xrefMetadata.toString(); // property @ entity
        this.list = list;
    }

    public EntityXrefMetadata getXrefMetadata() {
        return xrefMetadata;
    }

    public Class<? extends Entity<?>> getEntityType() {
        return entityType;
    }

    public String getEntityLabel() {
        return entityLabel;
    }

    public String getLabel() {
        return label;
    }

    public List<? extends Entity<?>> getList() {
        return list;
    }

}
