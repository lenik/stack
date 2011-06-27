package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class GenericEntityDao<E extends Entity<? extends K>, K extends Serializable>
        extends EntityDao<E, K> {

    public void setEntityType(Class<E> entityType) {
        if (entityType == null)
            throw new NullPointerException("entityType");
        this.objectType = entityType;
        this.entityType = entityType;
    }

    public void setKeyType(Class<K> keyType) {
        if (keyType == null)
            throw new NullPointerException("keyType");
        this.keyType = keyType;
    }

}
