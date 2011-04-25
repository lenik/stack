package com.bee32.plover.orm.entity;

import java.io.Serializable;

public class EntityAccessor {

    public static <K extends Serializable> void setId(Entity<K> entity, K id) {
        entity.setId(id);
    }

    public static <K extends Serializable> void setId(EntityBean<K> entity, K id) {
        entity.id = id;
    }

    public static void setVersion(Entity<?> entity, int version) {
        entity.version = version;
    }

    public static void setName(Entity<?> entity, String name) {
        entity._internalName(name);
    }

}
