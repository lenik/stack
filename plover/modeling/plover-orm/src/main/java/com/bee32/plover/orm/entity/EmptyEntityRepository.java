package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class EmptyEntityRepository<E extends EntityBean<K>, K extends Serializable>
        extends EntityRepository<E, K> {

    @Override
    public boolean containsKey(Serializable key) {
        return false;
    }

    @Override
    public List<? extends E> list() {
        return Collections.emptyList();
    }

    public List<? extends K> listKeys() {
        return Collections.emptyList();
    }

    @Override
    public E get(K key) {
        return null;
    }

    @Override
    public void deleteByKey(Serializable key) {
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

}
