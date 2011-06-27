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

@Service
@Lazy
// @Transactional(readOnly = true)
public class CommonDataManager {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    @Inject
    ApplicationContext applicationContext;

    @Inject
    DaoManager daoManager;

    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> access(Class<? extends E> entityType) {

        EntityDao<? super E, ? super K> dao = daoManager.getNearestDao(entityType);
        if (dao == null)
            throw new IllegalUsageException("No suitable EntityDao for entity " + entityType);

        EasTxWrapper<E, K> wrapper = applicationContext.getBean(EasTxWrapper.class);
        wrapper.setDao(dao);

        return wrapper;
    }

}
