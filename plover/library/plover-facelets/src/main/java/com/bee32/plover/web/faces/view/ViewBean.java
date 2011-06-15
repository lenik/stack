package com.bee32.plover.web.faces.view;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@ComponentTemplate
@Lazy
@Scope("view")
public abstract class ViewBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public ViewBean() {
        wire();
    }

    protected void wire() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }

    protected final ApplicationContext getApplicationContext() {
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
    protected final <T> T getBean(Class<T> beanType) {
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
    protected final Object getBeanByName(String beanName) {
        if (beanName == null)
            throw new NullPointerException("beanName");
        ApplicationContext context = getApplicationContext();
        return context.getBean(beanName);
    }

}
