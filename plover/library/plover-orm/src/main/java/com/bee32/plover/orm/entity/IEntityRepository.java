package com.bee32.plover.orm.entity;

import java.util.Collection;

import com.bee32.plover.arch.IRepository;
import com.bee32.plover.arch.naming.INamedNode;

public interface IEntityRepository<E extends IEntity<K>, K>
        extends IRepository<K, E>, INamedNode {

    /**
     * Or the storage class.
     */
    Class<? extends E> getEntityType();

    @Override
    K getKey(E entity);

    @Override
    Collection<? extends E> list();

    @Override
    Collection<? extends K> listKeys();

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
    boolean hasChild(Object entity);

    @Override
    String getChildName(Object entity);

}
