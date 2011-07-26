package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;

import com.bee32.plover.arch.IRepository;

public interface IEntityRepo<E extends IEntity<? extends K>, K extends Serializable>
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
    void saveAll(E... entities);

    @Override
    void saveAll(Collection<? extends E> entities);

    @Override
    void saveOrUpdateAll(E... entities);

    @Override
    void saveOrUpdateAll(Collection<? extends E> entities);

    @Override
    boolean delete(Object entity);

}
