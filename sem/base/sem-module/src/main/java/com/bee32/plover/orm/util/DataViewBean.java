package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.web.faces.view.GenericViewBean;

public abstract class DataViewBean
        extends GenericViewBean
        implements IEntityMarshalContext {

    private static final long serialVersionUID = 1L;

    static CommonDataManager getDataManager() {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        return dataManager;
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> serviceFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> //
    E loadEntity(Class<E> entityType, K id) {
        E entity = serviceFor(entityType).getOrFail(id);
        return entity;
    }

    protected static <E extends Entity<K>, K extends Serializable> //
    E reloadEntity(E entity) {
        Class<? extends E> entityType = (Class<? extends E>) entity.getClass();
        K id = entity.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        return reloaded;
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int selection) {

        if (!dto.getMarshalType().isReference())
            return dto;

        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = dto.getEntityType();
        K id = dto.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, selection, reloaded);
        return remarshalled;
    }
}
