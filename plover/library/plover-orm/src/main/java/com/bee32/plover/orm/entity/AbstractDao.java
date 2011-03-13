package com.bee32.plover.orm.entity;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 */
@ComponentTemplate
@Lazy
public abstract class AbstractDao<E extends IEntity<K>, K>
        extends GenericEntityRepository<E, K> {

    public AbstractDao(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(instanceType, entityType, keyType);
    }

    public AbstractDao(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public AbstractDao(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, instanceType, entityType, keyType);
    }

    public AbstractDao(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
    }

}
