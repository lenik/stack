package com.bee32.plover.orm.entity;

public abstract class AbstractDao<E extends IEntity<K>, K>
        extends HibernateEntityRepository<E, K> {

    public AbstractDao(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

}
