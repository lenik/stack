package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.naming.RepositoryNode;
import com.bee32.plover.arch.util.IStruct;

public abstract class EntityRepository<E extends IEntity<? extends K>, K extends Serializable>
        extends RepositoryNode<K, E>
        implements IEntityRepo<E, K> {

    static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    protected Class<E> entityType;

    public EntityRepository() {
        super();
    }

    public EntityRepository(String name) {
        super(name);
    }

    public EntityRepository(Class<E> entityType, Class<K> keyType) {
        super(keyType, entityType);
    }

    public EntityRepository(String name, Class<E> entityType, Class<K> keyType) {
        super(name, keyType, entityType);
    }

    @Override
    protected void introspect() {
        super.introspect();
        entityType = objectType;
    }

    @Override
    public Class<? extends E> getEntityType() {
        return entityType;
    }

    @Override
    public K getKey(E entity) {
        return entity.getId();
    }

    @Override
    public Collection<K> keys() {
        List<? extends E> list = list();
        List<K> keys = new ArrayList<K>();
        for (E entity : list) {
            K key = getKey(entity);
            keys.add(key);
        }
        return keys;
    }

    @Override
    public E populate(IStruct struct)
            throws BuildException {
        E entity;
        try {
            entity = getEntityType().newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(entity, struct);

        return entity;
    }

    @Override
    public abstract K save(E entity);

    @Override
    public abstract void update(E entity);

    @Override
    public abstract void refresh(E entity);

}
