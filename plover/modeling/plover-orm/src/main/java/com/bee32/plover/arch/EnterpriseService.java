package com.bee32.plover.arch;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.IEntityMarshalContext;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EnterpriseService
        extends Component
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager = MemdbDataManager.getInstance();

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
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

}
