package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmptyEntityRepository<E extends Entity<K>, K extends Serializable>
        extends EntityRepository<E, K> {

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public Collection<K> keys() {
        return Collections.emptyList();
    }

    @Override
    public List<E> list() {
        return Collections.emptyList();
    }

    @Override
    public E get(K key) {
        return null;
    }

    @Override
    public boolean deleteByKey(Serializable key) {
        return false;
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public K save(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(E entity) {
    }

    @Override
    public int count() {
        return 0;
    }

}
