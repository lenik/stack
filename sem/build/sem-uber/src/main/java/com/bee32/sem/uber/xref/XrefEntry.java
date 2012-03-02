package com.bee32.sem.uber.xref;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMetadata;

public class XrefEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int order;

    String entityTypeName;
    Serializable entityId;
    String entityLabel;

    EntityXrefMetadata metadata;
    String clientTypeName;
    Serializable clientId;
    String clientLabel;

    public XrefEntry(Entity<?> entity, EntityXrefMetadata metadata, Entity<?> client) {
        entityTypeName = ClassUtil.getParameterizedTypeName(entity);
        entityId = entity.getId();
        entityLabel = entity.getEntryLabel();

        this.metadata = metadata;

        clientTypeName = ClassUtil.getParameterizedTypeName(client);
        clientId = client.getId();
        clientLabel = client.getEntryLabel();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setEntity(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        entityId = entity.getId();
        entityLabel = entity.getEntryLabel();
        entityTypeName = ClassUtil.getParameterizedTypeName(entity);
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public Serializable getEntityId() {
        return entityId;
    }

    public String getEntityLabel() {
        return entityLabel;
    }

    public EntityXrefMetadata getMetadata() {
        return metadata;
    }

    public String getClientTypeName() {
        return clientTypeName;
    }

    public Serializable getClientId() {
        return clientId;
    }

    public String getClientLabel() {
        return clientLabel;
    }

}
