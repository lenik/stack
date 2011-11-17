package com.bee32.plover.orm.unit;

import java.util.Map;
import java.util.TreeMap;

import org.hibernate.type.Type;

public class EntityGraph {

    Class<?> entityType;
    Map<String, EntityXref> xrefs;

    public EntityGraph(PersistenceUnit unit, Class<?> entityType) {
        this.entityType = entityType;
        xrefs = new TreeMap<String, EntityXref>();
    }

    public void manyToOne(String userEntityName, Class<?> userEntityType, String userPropertyName, Type userPropertyType) {
        new EntityXref(entityName, userEntityType, userPropertyName, userPropertyType);
    }

}
