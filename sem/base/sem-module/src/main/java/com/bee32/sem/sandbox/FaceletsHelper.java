package com.bee32.sem.sandbox;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

public class FaceletsHelper {

    static ApplicationContext getApplicationContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
            throw new NullPointerException("facesContext");

        WebApplicationContext appContext = FacesContextUtils.getWebApplicationContext(facesContext);
        if (appContext == null)
            throw new NullPointerException("No application context in the Faces context");

        return appContext;
    }

    /**
     * Get a bean of specific type from application context.
     *
     * @param beanType
     *            Type of the bean to get, should not be <code>null</code>.
     * @return The bean of the given type.
     */
    static <T> T getBean(Class<T> beanType) {
        if (beanType == null)
            throw new NullPointerException("beanType");

        ApplicationContext context = getApplicationContext();

        Object bean = context.getBean(beanType);
        return beanType.cast(bean);
    }

    static CommonDataManager getDataManager() {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        return dataManager;
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> serviceFor(Class<E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().access(entityType);
        return service;
    }

}
