package com.bee32.plover.orm.entity;

import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
// @RepositoryTemplate
public abstract class GenericEntityRepository<E extends IEntity<K>, K>
        extends HibernateEntityRepository<E, K> {

    public GenericEntityRepository(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(instanceType, entityType, keyType);
    }

    public GenericEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public GenericEntityRepository(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, instanceType, entityType, keyType);
    }

    public GenericEntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
    }

}
