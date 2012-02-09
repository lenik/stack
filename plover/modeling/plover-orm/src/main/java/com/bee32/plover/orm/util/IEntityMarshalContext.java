package com.bee32.plover.orm.util;

import java.io.Serializable;

import javassist.tools.rmi.ObjectNotFoundException;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

public interface IEntityMarshalContext {

    /**
     * Load entity with the given id.
     *
     * <p>
     * Generally, this is implemented using {@link CommonDataManager#getOrFail(Class, Serializable)}.
     *
     * @param entityType
     *            The type of entity to load.
     * @param id
     *            The primary key of the entity.
     * @return Non-<code>null</code> loaded entity.
     * @throws ObjectNotFoundException
     *             If entity with specific id isn't existed.
     */
    <E extends Entity<K>, K extends Serializable> E getOrFail(Class<E> entityType, K id);

    <E extends Entity<K>, K extends Serializable> E getRef(Class<E> entityType, K id);

    /**
     * Get (Data-) Access-Service for specific entity type.
     */
    <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType);

    // <E extends EntityBean<K>, K extends Serializable> E mergeEntity(Class<E> entityType, K id);
}
