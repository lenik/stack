package com.bee32.plover.web.faces.utils;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public abstract class FacesContextSupport {

    protected static ApplicationContext getApplicationContext() {
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
    protected static <T> T getBean(Class<T> beanType) {
        if (beanType == null)
            throw new NullPointerException("beanType");

        ApplicationContext context = getApplicationContext();

        Object bean = context.getBean(beanType);
        return beanType.cast(bean);
    }

    /**
     * Get a named bean from application context.
     *
     * @param beanName
     *            Name of the bean to get, should not be <code>null</code>.
     * @return The bean with given name.
     */
    protected static Object getBeanByName(String beanName) {
        if (beanName == null)
            throw new NullPointerException("beanName");
        ApplicationContext context = getApplicationContext();
        return context.getBean(beanName);
    }

}
