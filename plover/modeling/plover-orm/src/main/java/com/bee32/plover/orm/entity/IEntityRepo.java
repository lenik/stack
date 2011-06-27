package com.bee32.plover.orm.entity;

import java.io.Serializable;

import com.bee32.plover.arch.IRepository;

public interface IEntityRepo<E extends IEntity<K>, K extends Serializable>
        extends IRepository<K, E> {

    Class<? extends E> getEntityType();

    // Override following methods to get eclipse "generate override methods"
    // to work.

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
    void saveOrUpdate(E entity);

    @Override
    void delete(Object entity);

}
