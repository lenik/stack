package com.bee32.plover.restful;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bee32.plover.arch.operation.OperationContext;

public class HttpRequestContext
        extends OperationContext {

    @SuppressWarnings("unchecked")
    public HttpRequestContext(HttpServletRequest req, HttpServletResponse resp) {
        super(req.getParameterMap());

        registerContext(HttpServletRequest.class, req);
        registerContext(HttpServletResponse.class, resp);

        HttpSession session = req.getSession();
        registerContext(HttpSession.class, session);

        ServletContext servletContext = session.getServletContext();
        registerContext(ServletContext.class, servletContext);
    }

}
