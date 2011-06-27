package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

@Service
@Lazy
@Transactional(readOnly = true)
public class CommonDataManager
// extends HibernateDaoSupport
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    Map<Class<?>, IEntityAccessService<?, ?>> emMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        emMap = new HashMap<Class<?>, IEntityAccessService<?, ?>>();
        for (IEntityAccessService<?, ?> em : applicationContext.getBeansOfType(IEntityAccessService.class).values()) {
            Class<?> entityType = em.getObjectType();
            emMap.put(entityType, em);
        }
    }

    public <E extends Entity<K>, K extends Serializable> IEntityAccessService<E, K> access(Class<? extends E> entityType) {
        IEntityAccessService<?, ?> dao = emMap.get(entityType);
        if (dao == null)
            throw new IllegalUsageException("No IEntityDao exists for entity " + entityType);

        @SuppressWarnings("unchecked")
        IEntityAccessService<E, K> daoCasted = (IEntityAccessService<E, K>) dao;
        return daoCasted;
    }

}
