package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.arch.util.IPartialContext;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

public abstract class AbstractDataPartialContext
        implements IPartialContext {

    public abstract CommonDataManager getDataManager();

    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> access(Class<? extends E> entityType) {
        return getDataManager().asFor(entityType);
    }

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
     *
     * @param entityType
     *            a persistent class
     * @param id
     *            the identifier of the persistent instance
     * @return the persistent instance
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *             if not found
     * @throws org.springframework.dao.DataAccessException
     *             in case of Hibernate errors
     */
    public <E extends Entity<K>, K extends Serializable> E getOrFail(Class<E> entityType, K id) {
        return access(entityType).getOrFail(id);
    }

    public <E extends Entity<K>, K extends Serializable> E getRef(Class<E> entityType, K id) {
        return access(entityType).lazyLoad(id);
    }

    public <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    public <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        if (dto == null)
            throw new NullPointerException("dto");
        if (!dto.getMarshalType().isReference())
            return dto;

        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = dto.getEntityType();
        K id = dto.getId();

        E reloaded = access(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, fmask, reloaded);
        return remarshalled;
    }

}
