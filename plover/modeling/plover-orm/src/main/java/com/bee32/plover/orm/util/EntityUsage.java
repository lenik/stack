package com.bee32.plover.orm.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityUsage {

    String usageId;
    Map<Class<?>, String> entityMap;

    public EntityUsage(String usageId) {
        this.usageId = usageId;
        this.entityMap = new LinkedHashMap<Class<?>, String>();
    }

    public Map<Class<?>, String> getEntityMap() {
        return entityMap;
    }

    public void add(Class<?> entityType, String description) {
        entityMap.put(entityType, description);
    }

}
