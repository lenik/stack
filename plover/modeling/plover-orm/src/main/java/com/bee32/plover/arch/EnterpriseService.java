package com.bee32.plover.arch;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EnterpriseService
        extends Component
        implements IUnmarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
        super(serviceName);
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        return dataManager.fetch(entityType, id);
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> D marshal(
            Class<D> dtoClass, E entity) {
        if (entity == null)
            return null;
        else
            return DTOs.marshal(dtoClass, entity);
    }

}
