package com.bee32.plover.faces.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class FacesContextUtils
        extends org.springframework.web.jsf.FacesContextUtils {

    public static ApplicationContext getApplicationContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
            throw new NullPointerException("facesContext");

        WebApplicationContext appContext = getWebApplicationContext(facesContext);
        if (appContext == null)
            throw new NullPointerException("No application context in the Faces context");

        return appContext;
    }

    public static HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object request = externalContext.getRequest();
        return (HttpServletRequest) request;
    }

    /**
     * Get a bean of specific type from application context.
     *
     * @param beanType
     *            Type of the bean to get, should not be <code>null</code>.
     * @return The bean of the given type.
     */
    public static <T> T getBean(Class<T> beanType) {
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
    public static Object getBeanByName(String beanName) {
        if (beanName == null)
            throw new NullPointerException("beanName");
        ApplicationContext context = getApplicationContext();
        return context.getBean(beanName);
    }

    public static UIComponent findComponent(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UIComponent component = viewRoot.findComponent(expr);

        if (component == null)
            throw new IllegalArgumentException("Illegal component id expr: " + expr);

        return component;
    }

    public static ComponentHelper findComponentEx(String expr) {
        UIComponent component = findComponent(expr);
        if (component == null)
            return null;

        return new ComponentHelper(component);
    }

}
