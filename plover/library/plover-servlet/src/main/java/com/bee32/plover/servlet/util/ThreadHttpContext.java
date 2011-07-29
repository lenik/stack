package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ThreadHttpContext
        extends ThreadServletContext {

    public static WebApplicationContext getWebApplicationContext() {
        ServletContext sc = getServletContextOpt();
        if (sc == null)
            return null;
        return WebApplicationContextUtils.getWebApplicationContext(sc);
    }

    public static ApplicationContext getApplicationContext() {
        return getWebApplicationContext();
    }

    public static WebApplicationContext requireWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    public static ApplicationContext requireApplicationContext() {
        return requireWebApplicationContext();
    }

    public static void autowireThisServlet(HttpServlet thisServlet) {
        AutowireCapableBeanFactory beanFactory = requireApplicationContext().getAutowireCapableBeanFactory();
        beanFactory.autowireBean(thisServlet);
    }

}
