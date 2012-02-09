package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.GlobalAppCtx;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class DefaultMarshalContext
        implements IEntityMarshalContext {

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    @Override
    public <E extends Entity<K>, K extends Serializable> E getOrFail(Class<E> entityType, K id) {
        E entity = dataManager.asFor(entityType).getOrFail(id);
        return entity;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E getRef(Class<E> entityType, K id) {
        E entity = dataManager.asFor(entityType).lazyLoad(id);
        return entity;
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> IEntityAccessService<E, K> asFor(
            Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

    public static IEntityMarshalContext getInstance() {
        IEntityMarshalContext emc = null;
        ApplicationContext applicationContext = ThreadHttpContext.getApplicationContext();

        if (applicationContext == null)
            applicationContext = GlobalAppCtx.getApplicationContext();

        if (applicationContext != null)
            emc = applicationContext.getBean(DefaultMarshalContext.class);
        else
            emc = new DefaultMarshalContext();

        return emc;
    }

}
