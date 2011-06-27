package com.bee32.plover.orm.dao;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.EasTxWrapper;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.IEntityAccessService;

@Service
@Lazy
@Transactional(readOnly = true)
public class CommonDataManager
// extends HibernateDaoSupport
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    @Inject
    ApplicationContext applicationContext;

    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> access(Class<? extends E> entityType) {
        EntityDao<E, K> dao = (EntityDao<E, K>) daoMap.get(entityType);
        if (dao == null)
            throw new IllegalUsageException("No EntityDao exists for entity " + entityType);

        EasTxWrapper<E, K> wrapper = applicationContext.getBean(EasTxWrapper.class);
        wrapper.setDao(dao);

        return wrapper;
    }

}
