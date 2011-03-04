package com.bee32.plover.orm.entity;

import java.util.Collections;
import java.util.List;

public class EmptyEntityRepository<E extends IEntity<K>, K>
        extends EntityRepository<E, K> {

    public EmptyEntityRepository(Class<E> instanceType, Class<K> keyType) {
        super(instanceType, keyType);
    }

    public EmptyEntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
    }

    @Override
    public boolean containsKey(Object key) {
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
    public E retrieve(Object key) {
        return null;
    }

    @Override
    public void deleteByKey(Object key) {
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
