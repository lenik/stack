package com.bee32.plover.servlet.container;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IAware;
import com.bee32.plover.inject.IContainer;

public class HttpServletAware
        implements IAware {

    protected ServletContext application;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Override
    public void enter(IContainer container)
            throws ContextException {
        application = container.require(ServletContext.class);
        request = container.require(HttpServletRequest.class);
        response = container.require(HttpServletResponse.class);
    }

    @Override
    public void leave(IContainer container)
            throws ContextException {
        application = null;
        request = null;
        response = null;
    }

}
