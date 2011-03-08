package com.bee32.plover.restful.integration;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SharedDispatcherServlet
        extends DispatcherServlet {

    private static final long serialVersionUID = 1L;

    static final ThreadLocal<SharedDispatcherServlet> threadLocal;
    static {
        threadLocal = new ThreadLocal<SharedDispatcherServlet>();
    }

    public SharedDispatcherServlet() {
        System.err.println("Set on " + Thread.currentThread());
        threadLocal.set(this);
    }

    public static ApplicationContext getThreadApplicationContext() {
        return getThreadWebApplicationContext();
    }

    public static WebApplicationContext getThreadWebApplicationContext() {
        System.err.println("Get on " + Thread.currentThread());
        SharedDispatcherServlet servlet = threadLocal.get();
        if (servlet == null)
            throw new IllegalStateException("DispatcherServlet hasn't been loaded, yet");

        WebApplicationContext context = servlet.getWebApplicationContext();
        if (context == null)
            throw new IllegalStateException("The web application context hasn't been set, yet");

        return context;
    }

}
