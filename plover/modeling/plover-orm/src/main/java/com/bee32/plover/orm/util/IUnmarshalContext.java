package com.bee32.plover.orm.util;

import java.io.Serializable;

import javassist.tools.rmi.ObjectNotFoundException;

import javax.free.IllegalUsageException;

import com.bee32.plover.orm.entity.Entity;

public interface IUnmarshalContext {

    /**
     * Load entity with the given id.
     *
     * @param entityType
     *            The type of entity to load.
     * @param id
     *            The primary key of the entity.
     * @return Non-<code>null</code> loaded entity.
     * @throws ObjectNotFoundException
     *             If entity with specific id isn't existed.
     */
    <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id);

    // <E extends EntityBean<K>, K extends Serializable> E mergeEntity(Class<E> entityType, K id);

    NullUnmarshalContext NULL = new NullUnmarshalContext();
}

class NullUnmarshalContext
        implements IUnmarshalContext {

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        throw new IllegalUsageException("No unmarshal context");
    }

}