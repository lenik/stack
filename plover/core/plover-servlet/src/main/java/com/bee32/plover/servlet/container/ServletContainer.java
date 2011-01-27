package com.bee32.plover.servlet.container;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bee32.plover.inject.AbstractContainer;

public class ServletContainer
        extends AbstractContainer
        implements ServletContextListener {

    public ServletContainer() {
    }

    public ServletContainer(ServletContext servletContext) {
        super(servletContext.getServletContextName());
        registerContext(ServletContext.class, servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        registerContext(ServletContext.class, servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        removeContext(ServletContext.class);
    }

    @Override
    public Object getFrame() {
        return null;
    }

    @Override
    public void setFrame(Object frameObject) {
    }

    public void service(Servlet servlet) {

    }

}
