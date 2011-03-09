package com.bee32.plover.orm.entity;

import com.bee32.plover.inject.ComponentTemplate;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 */
@ComponentTemplate
public abstract class AbstractDao<E extends IEntity<K>, K>
        extends HibernateEntityRepository<E, K> {

    public AbstractDao(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

}
