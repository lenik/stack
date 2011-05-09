package com.bee32.plover.servlet.util;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class ThreadHttpContext
        extends ThreadServletContext {

    public static WebApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(requireServletContext());
    }

    public static ApplicationContext getApplicationContext() {
        return getWebApplicationContext();
    }

    public static void autowireThisServlet(HttpServlet thisServlet) {
        AutowireCapableBeanFactory beanFactory = getApplicationContext().getAutowireCapableBeanFactory();
        beanFactory.autowireBean(thisServlet);
    }

}
