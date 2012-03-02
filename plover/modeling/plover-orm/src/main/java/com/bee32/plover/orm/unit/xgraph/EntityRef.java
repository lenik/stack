package com.bee32.plover.orm.unit.xgraph;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.entity.Entity;

@NotAComponent
public class EntityRef
        extends Entity<Integer> {

    private static final long serialVersionUID = 1L;

    int order;
    Entity<?> entity;
    EntityXrefMetadata metadata;
    Entity<?> client;

    public EntityRef(Entity<?> entity, EntityXrefMetadata metadata, Entity<?> client) {
        this.entity = entity;
        this.metadata = metadata;
        this.client = client;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    protected void setId(Integer id) {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Entity<?> getEntity() {
        return entity;
    }

    public void setEntity(Entity<?> entity) {
        this.entity = entity;
    }

    public EntityXrefMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(EntityXrefMetadata metadata) {
        this.metadata = metadata;
    }

    public Entity<?> getClient() {
        return client;
    }

    public void setClient(Entity<?> client) {
        this.client = client;
    }

}
