package com.bee32.plover.orm.util;

import java.io.Serializable;

import javassist.tools.rmi.ObjectNotFoundException;

import com.bee32.plover.orm.entity.Entity;

public interface IEntityMarshalContext {

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
}
