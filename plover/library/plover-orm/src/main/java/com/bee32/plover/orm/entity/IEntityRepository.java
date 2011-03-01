package com.bee32.plover.orm.entity;

import com.bee32.plover.arch.IRepository;
import com.bee32.plover.arch.locator.IObjectLocator;

public interface IEntityRepository<E extends IEntity<K>, K>
        extends IRepository<K, E>, IObjectLocator {

    /**
     * Or the storage class.
     */
    Class<? extends E> getEntityType();

    @Override
    K getKey(E entity);

    @Override
    boolean contains(Object entity);

    @Override
    K save(E entity);

    @Override
    void update(E entity);

    @Override
    void refresh(E entity);

    @Override
    K saveOrUpdate(E entity);

    @Override
    void delete(Object entity);

    @Override
    boolean isLocatable(Object entity);

    @Override
    String getLocation(Object entity);

}
