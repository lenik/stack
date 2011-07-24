package com.bee32.plover.orm.dao;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.plover.orm.entity.EasTxWrapper;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.IEntityMarshalContext;

@Service
@Lazy
// @Transactional(readOnly = true)
public class CommonDataManager
        implements IEntityMarshalContext {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    @Inject
    ApplicationContext applicationContext;

    @Inject
    DaoManager daoManager;

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {

        EntityDao<E, K> dao = daoManager.getDaoFor(entityType);
        if (dao == null)
            throw new IllegalUsageException("No suitable EntityDao for entity " + entityType);

        EasTxWrapper<E, K> wrapper = applicationContext.getBean(EasTxWrapper.class);
        // Class<?> etwc = wrapper.getClass();
        // logger.debug("ETW: " + etwc + " (" + System.identityHashCode(etwc) + ")");
        wrapper.setDao(dao);

        return wrapper;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        return asFor(entityType).getOrFail(id);
    }

}
