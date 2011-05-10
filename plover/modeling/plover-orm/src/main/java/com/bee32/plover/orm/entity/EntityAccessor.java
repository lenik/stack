package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Date;

import com.bee32.plover.orm.ext.color.EntityBean;

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

    public static void setCreatedDate(Entity<?> entity, Date createdDate) {
        if (createdDate == null)
            createdDate = new Date();
        entity.setCreatedDate(createdDate);
    }

    public static void setLastModified(Entity<?> entity, Date lastModified) {
        if (lastModified == null)
            lastModified = new Date();
        entity.setLastModified(lastModified);
    }

    public static EntityFlags getFlags(Entity<?> entity) {
        return entity.getEntityFlags();
    }

}
