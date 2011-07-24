package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Lazy
public class DefaultMarshalContext
        implements IEntityMarshalContext {

    @Inject
    CommonDataManager dataManager;

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        E entity = dataManager.access(entityType).getOrFail(id);
        return entity;
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> IEntityAccessService<E, K> asFor(
            Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = dataManager.access(entityType);
        return service;
    }

    public static final IEntityMarshalContext INSTANCE;
    static {
        ApplicationContext applicationContext = ThreadHttpContext.getApplicationContext();
        if (applicationContext != null) {
            INSTANCE = applicationContext.getBean(DefaultMarshalContext.class);
        } else {
            INSTANCE = new DefaultMarshalContext();
        }
    }

}
