package com.bee32.plover.orm.unit.xgraph;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.Type;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class EntityGraph {

    final Class<?> entityType;
    final String entityLabel;
    final List<EntityXrefMetadata> xrefs;

    public EntityGraph(PersistenceUnit unit, Class<?> entityType) {
        this.entityType = entityType;
        this.entityLabel = ClassUtil.getTypeName(entityType);
        xrefs = new ArrayList<EntityXrefMetadata>();
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public List<EntityXrefMetadata> getXrefs() {
        return xrefs;
    }

    public void manyToOne(Class<? extends Entity<?>> entityType, String propertyName, Type propertyType) {
        EntityXrefMetadata xrefMetadata = new EntityXrefMetadata(entityType, propertyName, propertyType);
        xrefs.add(xrefMetadata);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(xrefs.size() * 100);
        sb.append(entityLabel);
        sb.append(" {\n");
        for (EntityXrefMetadata xref : xrefs) {
            sb.append("    ");
            sb.append(xref);
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

}
