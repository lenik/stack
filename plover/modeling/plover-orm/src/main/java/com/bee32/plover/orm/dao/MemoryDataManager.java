package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntityAccessService;

@NotAComponent
public class MemoryDataManager
        extends CommonDataManager {

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> IEntityAccessService<E, K> asFor(
            Class<? extends E> entityType) {

        @SuppressWarnings("unchecked")
        Class<E> et = (Class<E>) entityType;
        Class<K> keyType = EntityUtil.getKeyType(et);

        return new MemoryDao<E, K>(et, keyType);
    }

}

class MemoryDao
        extends EntityRepository<Entity<?>, Serializable>
        implements IEntityAccessService<Entity<?>, Serializable> {

    static final Map<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>> database;
    static {
        database = new HashMap<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>>();
    }

    final MemoryDao parent;
    final Map<Serializable, Entity<?>> table;

    MemoryDao(Class<? extends Entity<?>> entityType) {
        super(entityType, EntityUtil.getKeyType(entityType));
        Map<Serializable, Entity<?>> table = database.get(entityType);
        if (table == null) {
            table = new TreeMap<Serializable, Entity<?>>();
            database.put(entityType, table);
        }
        this.table = table;

        MemoryDao parent = null;
        Class<?> _superType = entityType.getSuperclass();
        if (_superType != null) {
            if (Entity.class.isAssignableFrom(_superType)) {
                @SuppressWarnings("unchecked")
                Class<? extends Entity<?>> superType = (Class<? extends Entity<?>>) _superType;
                javax.persistence.Entity _entityAnnotation = _superType.getAnnotation(javax.persistence.Entity.class);
                if (_entityAnnotation != null) {
                    parent = new MemoryDao(superType);
                }
            }
        }
        this.parent = parent;
    }

    @Override
    public boolean containsKey(Object key) {
        return table.containsKey(key);
    }

    @Override
    public Entity<?> get(Serializable key) {
        return null;
    }

    @Override
    public List<Entity<?>> list() {
        return null;
    }

    @Override
    public void deleteByKey(Serializable key) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public int count() {
        return 0;
    }

}
