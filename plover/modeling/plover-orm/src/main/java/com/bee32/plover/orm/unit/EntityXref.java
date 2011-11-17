package com.bee32.plover.orm.unit;

import org.hibernate.type.Type;

public class EntityXref {

    String entityName;
    Class<?> entityType;
    String propertyName;
    Type propertyType;

    String label;
    boolean weak;

    public EntityXref(String entityName, Class<?> entityType, String propertyName, Type propertyType) {
        this.entityName = entityName;
        this.entityType = entityType;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    /**
     * weak ref should be "on delete set null", or otherwise not referenced at all.
     */
    public boolean isWeak() {
        return weak;
    }

}
