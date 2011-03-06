package com.bee32.plover.orm.entity;

import com.bee32.plover.arch.IRepository;
import com.bee32.plover.arch.naming.INamedNode;

public interface IEntityRepository<E extends IEntity<K>, K>
        extends IRepository<K, E>, INamedNode {

    /**
     * Or the storage class.
     */
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
    K saveOrUpdate(E entity);

    @Override
    void delete(Object entity);

    @Override
    boolean hasChild(Object entity);

    @Override
    String getChildName(Object entity);

}
