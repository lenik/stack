package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapEntityRepository<E extends Entity<K>, K extends Serializable>
        extends EntityRepository<E, K>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<K, E> map;

    public MapEntityRepository() {
        super();
    }

    public MapEntityRepository(String name) {
        super(name);
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
    public boolean containsKey(Object key) {
        return getMap().containsKey(key);
    }

    @Override
    public List<E> list() {
        List<E> list = new ArrayList<E>();
        list.addAll(getMap().values());
        return list;
    }

    @Override
    public Set<K> keys() {
        return getMap().keySet();
    }

    @Override
    public E get(K key) {
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

    @Override
    public void saveByNaturalId(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveOrUpdateByNaturalId(E entity) {
        throw new UnsupportedOperationException();
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
    public boolean deleteByKey(Serializable key) {
        E removed = getMap().remove(key);
        return removed != null;
    }

    @Override
    public void deleteAll() {
        getMap().clear();
    }

    @Override
    public int count() {
        return getMap().size();
    }

}
