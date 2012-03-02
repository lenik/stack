package com.bee32.plover.orm.unit.xgraph;

import java.io.Serializable;

import org.hibernate.type.Type;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;

/**
 * @see EntityGraphTool#buildGraphs(org.hibernate.SessionFactory)
 */
public class EntityXrefMetadata
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Class<? extends Entity<?>> entityType;
    final String entityTypeName;
    final String propertyName;

    final Type propertyType;
    final Class<?> propertyClass;
    final String propertyTypeName;

    String label;
    boolean weak;

    public EntityXrefMetadata(Class<? extends Entity<?>> entityType, String propertyName, Type propertyType) {
        if (entityType == null)
            throw new NullPointerException("entityType");
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        if (propertyType == null)
            throw new NullPointerException("propertyType");

        this.entityType = entityType;
        this.entityTypeName = ClassUtil.getTypeName(entityType);
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyClass = propertyType.getReturnedClass();

        // Enh: get property NLS instead of type display name.
        this.propertyTypeName = ClassUtil.getTypeName(propertyClass);

        // ManyToOneType manyToOneType = (ManyToOneType) propertyType;
        // manyToOneType.getAssociatedEntityName();
    }

    public Class<? extends Entity<?>> getEntityType() {
        return entityType;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Type getPropertyType() {
        return propertyType;
    }

    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public String getLabel() {
        return label;
    }

    /**
     * weak ref should be "on delete set null", or otherwise not referenced at all.
     */
    public boolean isWeak() {
        return weak;
    }

    /**
     * Output:
     *
     * entityName.propertyName: propertyType
     */
    @Override
    public String toString() {
        return propertyName + " ◎ " + entityTypeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += entityType.hashCode();
        hash += propertyName.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntityXrefMetadata))
            return false;
        EntityXrefMetadata o = (EntityXrefMetadata) obj;
        if (!o.entityType.equals(entityType))
            return false;
        if (!o.propertyName.equals(propertyName))
            return false;
        return true;
    }

}
