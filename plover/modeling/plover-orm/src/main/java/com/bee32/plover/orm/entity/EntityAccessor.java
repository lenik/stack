package com.bee32.plover.orm.entity;

import java.io.Serializable;

public class EntityAccessor {

    public static <K extends Serializable> void setId(EntityBean<K> entity, K id) {
        entity.id = id;
    }

    public static void setVersion(EntityBean<?> entity, int version) {
        entity.version = version;
    }

    public static void setName(EntityBean<?> entity, String name) {
        entity._internalName(name);
    }

}
