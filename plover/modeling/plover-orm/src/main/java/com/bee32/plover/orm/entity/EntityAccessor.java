package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Date;

public class EntityAccessor {

    public static <K extends Serializable> void setId(Entity<K> entity, K id) {
        entity.setId(id);
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

    static final boolean useAutoIdAnnotation = false;

    /**
     * Auto-id attribute maybe used for:
     * <ul>
     * <li>Determine whether an entity is new-created by test id==null.
     * <li>For sample instances, whether update(spec) or insert(auto) should be done. To reload
     * samples, insert(auto) should always be skipped.
     * </ul>
     */
    public static boolean isAutoId(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        if (useAutoIdAnnotation) {
            _AutoId _autoId = entity.getClass().getAnnotation(_AutoId.class);
            if (_autoId == null)
                return false;
            return true;
        }

        return entity.autoId;
    }

}
