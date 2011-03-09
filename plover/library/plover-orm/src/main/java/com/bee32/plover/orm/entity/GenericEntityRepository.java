package com.bee32.plover.orm.entity;

import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
//@RepositoryTemplate
public abstract class GenericEntityRepository<E extends IEntity<K>, K>
        extends HibernateEntityRepository<E, K> {

    public GenericEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

}
