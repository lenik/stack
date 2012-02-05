package com.bee32.plover.arch;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.site.scope.PerSite;

@Transactional(readOnly = true)
@ComponentTemplate
@PerSite
public abstract class DataService
        extends Component
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager = MemdbDataManager.getInstance();

    public DataService() {
        super();
    }

    public DataService(String serviceName) {
        super(serviceName);
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        return asFor(entityType).getOrFail(id);
    }

    @Override
    public <_E extends Entity<? extends _K>, _K extends Serializable> //
    IEntityAccessService<_E, _K> asFor(Class<? extends _E> entityType) {
        IEntityAccessService<_E, _K> service = dataManager.asFor(entityType);
        return service;
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    protected <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int fmask) {
        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = DTOs.getEntityType(dto);
        K id = dto.getId();

        E reloaded = asFor(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, fmask, reloaded);
        return remarshalled;
    }

}
