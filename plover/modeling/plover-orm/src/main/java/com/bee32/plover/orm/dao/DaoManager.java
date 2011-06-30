package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.GenericEntityDao;

@Component
@Lazy
public class DaoManager
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(DaoManager.class);

    static Map<Class<?>, EntityDao<?, ?>> daoMap;
    static Map<Class<?>, EntityDao<?, ?>> daoCache;

    ApplicationContext applicationContext;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        this.applicationContext = applicationContext;

        if (daoMap != null)
            return;

        daoMap = new HashMap<Class<?>, EntityDao<?, ?>>();

        for (EntityDao<?, ?> dao : applicationContext.getBeansOfType(EntityDao.class).values()) {
            Class<?> baseEntityType = dao.getEntityType();
            daoMap.put(baseEntityType, dao);
        }
    }

    public <E extends Entity<? extends K>, K extends Serializable> EntityDao<E, K> //
    getDaoFor(Class<? extends E> entityType) {

        EntityDao<E, K> dao = null;

        dao = (EntityDao<E, K>) daoMap.get(entityType);
        if (dao == null) {
            logger.warn("DAO for " + entityType + " isn't defined, create in default manner.");

            GenericEntityDao<E, K> genericDao = applicationContext.getBean(GenericEntityDao.class);

            @SuppressWarnings("unchecked")
            Class<E> _entityType = (Class<E>) entityType;
            Class<K> _keyType = EntityUtil.getKeyType(_entityType);

            genericDao.setEntityType(_entityType);
            genericDao.setKeyType(_keyType);

            dao = genericDao;
            daoMap.put(entityType, dao);
        }

        return dao;
    }

    public <E extends Entity<? extends K>, K extends Serializable> EntityDao<? super E, ? super K> //
    getNearestDao(Class<? extends E> entityType) {
        EntityDao<? super E, ? super K> dao = null;

        Class<?> baseType = (Class<?>) entityType;
        while (baseType != null) {

            dao = (EntityDao<? super E, ? super K>) daoMap.get(baseType);
            if (dao != null)
                break;

            if (baseType == Entity.class)
                break;
            baseType = baseType.getSuperclass();
        }

        return dao;
    }

}
