package com.bee32.plover.restful.context;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SimpleApplicationContextUtil {

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
        Object obj = servletContext.getAttribute(rootApplicationContextKey);

        if (obj == null)
            return WebApplicationContextUtils.getWebApplicationContext(servletContext);

        if (!(obj instanceof ApplicationContext))
            throw new IllegalUsageException("The root application context is not an instance of ApplicationContext");

        ApplicationContext applicationContext = (ApplicationContext) obj;
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

}
