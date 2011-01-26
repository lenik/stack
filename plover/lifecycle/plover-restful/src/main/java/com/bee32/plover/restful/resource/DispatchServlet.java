package com.bee32.plover.restful.resource;

import java.io.IOException;

import javax.free.NotImplementedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        while (path.startsWith("/"))
            path = path.substring(1);

        IResource resource = ResourceDispatcher.dispatch(path);
        if (resource == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource: " + path);
            return;
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

}
