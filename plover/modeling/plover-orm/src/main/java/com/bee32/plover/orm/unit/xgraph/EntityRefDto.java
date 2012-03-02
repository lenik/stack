package com.bee32.plover.orm.unit.xgraph;

import java.io.Serializable;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class EntityRefDto<K extends Serializable>
        extends EntityDto<EntityRef, Integer> {

    private static final long serialVersionUID = 1L;

    int order;

    String entityTypeName;
    Serializable entityId;
    String entityLabel;

    EntityXrefMetadata metadata;
    String clientTypeName;
    Serializable clientId;
    String clientLabel;

    @Override
    protected void _marshal(EntityRef source) {
        Entity<?> entity = source.getEntity();
        Entity<?> client = source.getClient();

        entityTypeName = ClassUtil.getParameterizedTypeName(entity);
        entityId = entity.getId();
        entityLabel = entity.getEntryLabel();

        metadata = source.getMetadata();

        clientTypeName = ClassUtil.getParameterizedTypeName(client);
        clientId = client.getId();
        clientLabel = client.getEntryLabel();
    }

    @Override
    protected void _unmarshalTo(EntityRef target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
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
