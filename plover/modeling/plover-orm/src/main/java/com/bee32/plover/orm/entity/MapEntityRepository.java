package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapEntityRepository<E extends EntityBean<K>, K extends Serializable>
        extends EntityRepository<E, K>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<K, E> map;

    public MapEntityRepository(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(instanceType, entityType, keyType);
    }

    public MapEntityRepository(Class<E> instanceType, Class<K> keyType) {
        super(instanceType, keyType);
    }

    public MapEntityRepository(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, instanceType, entityType, keyType);
    }

    public MapEntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
    }

    public Map<K, E> getMap() {
        if (map == null) {
            synchronized (this) {
                if (map == null)
                    map = createMap();
            }
        }
        return map;
    }

    protected Map<K, E> createMap() {
        return new HashMap<K, E>();
    }

    @Override
    public boolean containsKey(Serializable key) {
        return getMap().containsKey(key);
    }

    @Override
    public Collection<? extends E> list() {
        return getMap().values();
    }

    @Override
    public Collection<? extends K> listKeys() {
        return getMap().keySet();
    }

    @Override
    public E retrieve(Serializable key) {
        return getMap().get(key);
    }

    @Override
    public K save(E entity) {
        K key = getKey(entity);
        getMap().put(key, entity);
        return key;
    }

    @Override
    public void update(E entity) {
        K key = getKey(entity);
        getMap().put(key, entity);
    }

    public void refresh(E entity) {
        K key = getKey(entity);
        E backedState = getMap().get(key);
        if (entity instanceof IPopulatable) {
            IPopulatable populatable = (IPopulatable) entity;
            populatable.populate(backedState);
        }
    }

    @Override
    public void deleteByKey(Serializable key) {
        getMap().remove(key);
    }

    @Override
    public void deleteAll() {
        getMap().clear();
    }

    public int count() {
        return getMap().size();
    }

}
