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

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.entity.NonUniqueException;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class MemoryDao
        extends EntityRepository<Entity<?>, Serializable>
        implements IEntityAccessService<Entity<?>, Serializable> {

    // Set to true, the root node would be for Entity.class.
    static final Map<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>> database;
    static {
        database = new HashMap<Class<? extends Entity<?>>, Map<Serializable, Entity<?>>>();
    }

    final MemoryDao parent;
    final List<MemoryDao> children = new ArrayList<MemoryDao>();

    final Map<Serializable, Entity<?>> table;

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

                boolean isInheritedFromSuper = true;
                isInheritedFromSuper = _superType.getAnnotation(javax.persistence.Entity.class) != null;

                if (isInheritedFromSuper)
                    parent = getInstance(superType);
            }
        }
        this.parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    void addChild(MemoryDao childDao) {
        if (children.contains(childDao))
            throw new IllegalUsageError("Duplicated child memdao: " + childDao);
        children.add(childDao);
    }

    protected MemoryDao access(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) entity.getClass();
        if (this.entityType.equals(entityType))
            return this;

        if (!this.entityType.isAssignableFrom(entityType))
            throw new IllegalArgumentException("Can't handle entity of " + entityType //
                    + " by a " + this.entityType.getSimpleName() + " memory-dao.");

        MemoryDao specDao = getInstance(entityType);
        return specDao;
    }

    @Override
    public boolean containsKey(Object key) {
        return table.containsKey(key);
    }

    @Override
    public Entity<?> get(Serializable key) {
        Entity<?> _o = table.get(key);
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
    public synchronized boolean deleteByKey(Serializable key) {
        Entity<?> removed = table.remove(key);

        for (MemoryDao child : children)
            child.deleteByKey(key);

        return removed != null;
    }

    @Override
    public synchronized void deleteAll() {
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
    public final Entity<?> lazyLoad(Serializable id) {
        return get(id);
    }

    @Override
    public final Entity<?> retrieve(Serializable key, LockMode lockMode)
            throws DataAccessException {
        return get(key);
    }

    @Override
    public final void update(Entity<?> entity, LockMode lockMode)
            throws DataAccessException {
        update(entity);
    }

    @Override
    public final boolean delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        return delete(entity);
    }

    @Deprecated
    @Override
    public synchronized Entity<?> merge(Entity<?> entity)
            throws DataAccessException {
        MemoryDao spec = access(entity);
        if (spec != this)
            return spec.merge(entity);

        @SuppressWarnings("unchecked")
        Entity<Serializable> _o = (Entity<Serializable>) dup(entity);

        Serializable id = _o.getId();
        if (id == null) {
            id = SimpleIdGenerator.generate(entity);
            EntityAccessor.setId(_o, id);
        }

        _put(id, _o);
        return dup(_o);
    }

    void _put(Serializable id, Entity<?> _o) {
        MemoryDao node = this;
        while (node != null) {
            node.table.put(id, _o);
            node = node.parent;
        }
    }

    @Deprecated
    @Override
    public void evict(Entity<?> entity)
            throws DataAccessException {
        // do nothing here.
        for (MemoryDao child : children)
            child.evict(entity);
    }

    @Deprecated
    @Override
    public void replicate(Entity<?> entity, ReplicationMode replicationMode)
            throws DataAccessException {
        // do nothing here.
        for (MemoryDao child : children)
            child.replicate(entity, replicationMode);
    }

    @Deprecated
    @Override
    public void flush() {
        // do nothing here.
        for (MemoryDao child : children)
            child.flush();
    }

    // IEntityAccessService.

    @Override
    public Entity<?> getUnique(ICriteriaElement... criteriaElements) {
        List<Entity<?>> list = list(criteriaElements);
        if (list.isEmpty())
            return null;
        if (list.size() > 1)
            throw new NonUniqueException();
        return list.get(0);
    }

    @Override
    public Entity<?> getFirst(ICriteriaElement... criteriaElements) {
        CriteriaComposite optim = new CriteriaComposite(criteriaElements, new Limit(0, 1));
        List<Entity<?>> list = list(optim);
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public Entity<?> getByName(String name) {
        return getFirst(new Equals("name", name));
    }

    @Override
    public int count(ICriteriaElement... criteriaElements) {
        return list(criteriaElements).size();
    }

    @Override
    public final boolean deleteById(Serializable id) {
        return deleteByKey(id);
    }

    @Override
    public void deleteAll(ICriteriaElement... criteriaElements) {
        List<Entity<?>> list = list(criteriaElements);
        for (Entity<?> entity : list)
            delete(entity);
    }

    @Override
    public List<Entity<?>> list(ICriteriaElement... criteriaElements) {
        throw new NotImplementedException();
    }

    // ER.

    @Override
    public Serializable save(Entity<?> _entity) {
        @SuppressWarnings("unchecked")
        Entity<Serializable> entity = (Entity<Serializable>) _entity;

        MemoryDao spec = access(entity);
        if (spec != this)
            return spec.save(entity);

        Serializable id = entity.getId();
        if (id != null) {
            // is not auto-generated?
        }

        if (id == null) {
            id = SimpleIdGenerator.generate(entity);
            EntityAccessor.setId(entity, id);
        }

        Entity<Serializable> _o = dup(entity);
        _put(id, _o);
        return id;
    }

    @Override
    public void update(Entity<?> entity) {
        MemoryDao spec = access(entity);
        if (spec != this) {
            spec.update(entity);
            return;
        }

        Serializable id = entity.getId();
        if (id == null)
            throw new IllegalStateException("Entity hasn't been saved yet: " + entity);

        Entity<?> _o = dup(entity);
        _put(id, _o);
    }

    @Override
    public void refresh(Entity<?> entity) {
        MemoryDao spec = access(entity);
        if (spec != this) {
            spec.refresh(entity);
            return;
        }
        Serializable id = entity.getId();
        if (id == null)
            throw new IllegalStateException("Entity hasn't been persisted: " + entity);

        Entity<?> existing = getOrFail(id);
        entity.populate(existing); // may not been well implemented by specific entity types.
    }

    static final Map<Class<? extends Entity<?>>, MemoryDao> instances = new HashMap<Class<? extends Entity<?>>, MemoryDao>();

    public static synchronized MemoryDao getInstance(Class<? extends Entity<?>> entityType) {
        MemoryDao instance = instances.get(entityType);
        if (instance == null) {
            instance = new MemoryDao(entityType);
        }
        return instance;
    }

    public static synchronized void refreshForUnit(PersistenceUnit unit) {
        instances.clear();

        for (Class<?> _class : unit.getClasses()) {

            @SuppressWarnings("unchecked")
            Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) _class;

            getInstance(entityType);
        }
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

}
