package com.bee32.plover.orm.entity;

import com.bee32.icsf.access.resource.Resource;

/**
 * @see EntityResourceNS
 */
public class EntityResource
        extends Resource {

    public EntityResource(Class<? extends IEntity<?>> entityClass) {
        super(getEntityName(entityClass));
    }

    public EntityResource(Class<? extends IEntity<?>> entityClass, String member) {
        super(getEntityName(entityClass, member));
    }

    static String getEntityName(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        String name = clazz.getSimpleName();
        return name;
    }

    static String getEntityName(Class<?> clazz, String member) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (member == null)
            throw new NullPointerException("member");

        String baseName = getEntityName(clazz);
        return baseName + "." + member;
    }

}
