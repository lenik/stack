package com.bee32.plover.servlet.context;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletContextUtil {

    /**
     * @see org.springframework.web.context.WebApplicationContext
     * @see org.springframework.web.context.support.WebApplicationContextUtils
     */
    public static final String rootApplicationContextKey;
    public static final String webRootApplicationContextKey;
    static {
        rootApplicationContextKey = ApplicationContext.class.getName() + ".ROOT";
        webRootApplicationContextKey = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    }

    public static ApplicationContext getApplicationContext(ServletContext servletContext) {
        Object rootAppCtx = servletContext.getAttribute(rootApplicationContextKey);

        if (rootAppCtx == null)
            return WebApplicationContextUtils.getWebApplicationContext(servletContext);

        if (!(rootAppCtx instanceof ApplicationContext))
            throw new IllegalUsageException("The root application context is not an instance of ApplicationContext");

        ApplicationContext applicationContext = (ApplicationContext) rootAppCtx;
        return applicationContext;
    }

    static void _setApplicationContext(ServletContext servletContext, ApplicationContext applicationContext) {
        if (applicationContext instanceof WebApplicationContext) {
            servletContext.removeAttribute(rootApplicationContextKey);
            servletContext.setAttribute(webRootApplicationContextKey, applicationContext);
        }

        else {
            servletContext.removeAttribute(webRootApplicationContextKey);
            servletContext.setAttribute(rootApplicationContextKey, applicationContext);
        }
    }

    public static void wire(ServletContext servletContext, Object bean) {
        ApplicationContext applicationContext = getApplicationContext(servletContext);
        if (applicationContext == null)
            throw new IllegalStateException("Servlet context " + servletContext + " doesn't have application context");

        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        factory.autowireBean(bean);
    }

}
