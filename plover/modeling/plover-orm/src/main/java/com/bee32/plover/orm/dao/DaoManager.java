package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;

@Component
@Lazy
public class DaoManager
        implements ApplicationContextAware {

    static Map<Class<?>, EntityDao<?, ?>> daoMap;
    static Map<Class<?>, EntityDao<?, ?>> daoCache;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        if (daoMap != null)
            return;

        daoMap = new HashMap<Class<?>, EntityDao<?, ?>>();

        for (EntityDao<?, ?> dao : applicationContext.getBeansOfType(EntityDao.class).values()) {
            Class<?> baseEntityType = dao.getEntityType();
            daoMap.put(baseEntityType, dao);
        }
    }

    public <E extends Entity<? extends K>, K extends Serializable> EntityDao<? super E, ? super K> //
    getNearestDao(Class<? extends E> entityType) {
        EntityDao<E, K> dao = daoMap.get(entityType);
    }

}
