package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.GenericDao;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 *
 * @see GenericDao
 */
@Lazy
@ComponentTemplate
public abstract class EntityDao<E extends IEntity<K>, K extends Serializable>
        extends GenericEntityRepository<E, K> {

    public EntityDao(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(instanceType, entityType, keyType);
    }

    public EntityDao(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public EntityDao(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, instanceType, entityType, keyType);
    }

    public EntityDao(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
    }

}
