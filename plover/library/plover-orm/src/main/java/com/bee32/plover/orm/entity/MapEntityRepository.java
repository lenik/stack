package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.free.ParserUtil;

public class MapEntityRepository<E extends IEntity<K>, K>
        extends EntityRepository<E, K>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<K, E> map;

    public MapEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
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
    public E retrieve(Object key) {
        return getMap().get(key);
    }

    @Override
    public void save(K key, E obj) {
        getMap().put(key, obj);
    }

    @Override
    public void update(K key, E obj) {
        getMap().put(key, obj);
    }

    @Override
    public void deleteByKey(Object key) {
        getMap().remove(key);
    }

    @Override
    public void deleteAll() {
        getMap().clear();
    }

    public int count() {
        return getMap().size();
    }

    @Override
    protected K parseKey(String location)
            throws ParseException {
        if (keyType == String.class)
            return keyType.cast(location);
        return ParserUtil.parse(keyType, location);
    }

}
