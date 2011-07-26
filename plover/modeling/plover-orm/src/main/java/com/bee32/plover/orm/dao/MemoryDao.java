package com.bee32.plover.orm.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.free.IllegalUsageError;
import javax.free.NotImplementedException;
import javax.free.UnexpectedException;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.springframework.dao.DataAccessException;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class MemoryDao
        extends EntityRepository<Entity<?>, Serializable>
        implements IEntityAccessService<Entity<?>, Serializable> {

// Set to true, the root node would be for Entity.class.
    static final boolean virtualRoot = true;
    static final Map<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>> database;
    static {
        database = new HashMap<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>>();
    }

    final MemoryDao parent;
    final List<MemoryDao> children = new ArrayList<MemoryDao>();

    final Map<Serializable, Entity<?>> table;

    static void scanAll() {
        PersistenceUnit unit = CustomizedSessionFactoryBean.getForceUnit();
        for (Class<?> entityType : unit.getClasses()) {

        }
    }

    private MemoryDao(Class<? extends Entity<?>> entityType) {
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

                boolean included = true;
                if (!virtualRoot)
                    included = _superType.getAnnotation(javax.persistence.Entity.class) != null;

                if (included)
                    parent = getInstance(superType);
            }
        }
        this.parent = parent;

        if (parent != null)
            parent.addChild(this);
    }

    static final Map<Class<? extends Entity<?>>, MemoryDao> instances = new HashMap<Class<? extends Entity<?>>, MemoryDao>();

    static synchronized MemoryDao getInstance(Class<? extends Entity<?>> entityType) {
        MemoryDao instance = instances.get(entityType);
        if (instance == null) {
            instance = new MemoryDao(entityType);
        }
        return instance;
    }

    void addChild(MemoryDao childDao) {
        if (children.contains(childDao))
            throw new IllegalUsageError("Duplicated child memdao: " + childDao);
        children.add(childDao);
    }

    /**
     * Clone an object thru java serialization.
     */
    static <T extends Serializable> T dup(T obj) {
        if (obj == null)
            return null;

        try {
            ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bufOut);
            out.writeObject(obj);
            out.flush();

            byte[] data = bufOut.toByteArray();

            ByteArrayInputStream bufIn = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(bufIn);
            T copy = (T) in.readObject();

            return copy;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (table.containsKey(key))
            return true;

        for (MemoryDao child : children)
            if (child.contains(key))
                return true;

        return false;
    }

    @Override
    public Entity<?> get(Serializable key) {
        Entity<?> _o = table.get(key);
        if (_o == null)
            for (MemoryDao child : children) {
                _o = child.get(key);
                if (_o != null)
                    break;
            }
        if (_o == null)
            return null;
        else
            return dup(_o);
    }

    @Override
    public List<Entity<?>> list() {
        ArrayList<Entity<?>> copy = new ArrayList<Entity<?>>();
        for (Entity<?> _o : table.values()) {
            copy.add(dup(_o));
        }
        return copy;
    }

    @Override
    public boolean deleteByKey(Serializable key) {
        Entity<?> removed = table.remove(key);
        if (removed != null)
            return true;

        for (MemoryDao child : children) {
            if (child.deleteByKey(key))
                return true;
        }

        return false;
    }

    @Override
    public void deleteAll() {
        table.clear();

        for (MemoryDao child : children)
            child.deleteAll();
    }

    @Override
    public int count() {
        return table.size();
    }

    // IEntityRepo_H

    @Override
    public Entity<?> lazyLoad(Serializable id) {
        return get(id);
    }

    @Override
    public Entity<?> retrieve(Serializable key, LockMode lockMode)
            throws DataAccessException {
        return get(key);
    }

    @Override
    public void update(Entity<?> entity, LockMode lockMode)
            throws DataAccessException {
        update(entity);
    }

    @Override
    public boolean delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        return delete(entity);
    }

    @Override
    public Entity<?> merge(Entity<?> entity)
            throws DataAccessException {

        Class<?> entityType = entity.getClass();
        // getInstance(entityType)

        Entity<?> _o = dup(entity);

        throw new NotImplementedException();
    }

    @Override
    public void evict(Entity<?> entity)
            throws DataAccessException {
        // do nothing here.
    }

    @Override
    public void replicate(Entity<?> entity, ReplicationMode replicationMode)
            throws DataAccessException {
        // do nothing here.
    }

    @Override
    public void flush() {
        // do nothing here.
    }

    // IEas.

    @Override
    public Entity<?> getUnique(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    @Override
    public Entity<?> getFirst(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    @Override
    public Entity<?> getByName(String name) {
        throw new NotImplementedException();
    }

    @Override
    public List<Entity<?>> list(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    @Override
    public int count(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteById(Serializable id) {
        return deleteByKey(id);
    }

    @Override
    public void deleteAll(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    // ER.

    @Override
    public Serializable save(Entity<?> entity) {
        Class<?> entityType = entity.getClass();
        // getInstance

        Entity<?> _o = dup(entity);

        throw new NotImplementedException();
    }

    @Override
    public void update(Entity<?> entity) {
        // do nothing.
    }

    @Override
    public void refresh(Entity<?> entity) {
        // do nothing.
    }

}
