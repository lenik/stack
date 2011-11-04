package com.bee32.plover.orm.dao;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.free.Order;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EasTxWrapper;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.site.scope.PerSite;

// @Transactional(readOnly = true)
@Service
@PerSite
public class CommonDataManager
        implements IEntityMarshalContext, ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    @Inject
    ApplicationContext applicationContext;

    @Inject
    DaoManager daoManager;

    private Class<? extends EasTxWrapper<?, ?>> etwImplClass = null;
    private int etwOrder = Integer.MAX_VALUE;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        for (EasTxWrapper<?, ?> etw : applicationContext.getBeansOfType(EasTxWrapper.class).values()) {

            Class<? extends EasTxWrapper<?, ?>> etwClass //
            /*    */= (Class<? extends EasTxWrapper<?, ?>>) ClassUtil.skipProxies(etw.getClass());

            Order _order = etwClass.getAnnotation(Order.class);
            int order = _order == null ? Integer.MAX_VALUE : _order.value();

            if (order < etwOrder || etwImplClass == null) {
                etwImplClass = (Class<? extends EasTxWrapper<?, ?>>) etwClass;
                etwOrder = order;
            }
        }

        if (etwImplClass == null)
            throw new IllegalUsageException("No available ETW implementation.");

        logger.debug("Preferred ETW Implementation: " + etwImplClass + " (order=" + etwOrder + ")");
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {

        EntityDao<E, K> dao = daoManager.getDaoFor(entityType);
        if (dao == null)
            throw new IllegalUsageException("No suitable EntityDao for entity " + entityType);

        EasTxWrapper<E, K> preferredEtw = (EasTxWrapper<E, K>) applicationContext.getBean(etwImplClass);

        // Class<?> etwc = wrapper.getClass();
        // logger.debug("ETW: " + etwc + " (" + System.identityHashCode(etwc) + ")");
        preferredEtw.setDao(dao);

        return preferredEtw;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        return asFor(entityType).getOrFail(id);
    }

}
