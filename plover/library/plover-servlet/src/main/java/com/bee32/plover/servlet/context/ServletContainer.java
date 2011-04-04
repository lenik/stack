package com.bee32.plover.servlet.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bee32.plover.inject.AbstractContainer;

public class ServletContainer
        extends AbstractContainer
        implements ServletContextListener {

    public ServletContainer(ServletContext servletContext) {
        super(servletContext.getServletContextName());
        registerContext(ServletContext.class, servletContext);
    }

    public ServletContainer(HttpServletRequest request, HttpServletResponse response) {
        super(request.getRequestURI().toString());

        // registerContext(ServletRequest.class, request);
        // registerContext(ServletResponse.class, response);
        // if (request instanceof HttpServletRequest)
        registerContext(HttpServletRequest.class, (HttpServletRequest) request);
        // if (response instanceof HttpServletResponse)
        registerContext(HttpServletResponse.class, (HttpServletResponse) request);

        HttpSession session = request.getSession();
        registerContext(HttpSession.class, session);

        ServletContext servletContext = session.getServletContext();
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

}
