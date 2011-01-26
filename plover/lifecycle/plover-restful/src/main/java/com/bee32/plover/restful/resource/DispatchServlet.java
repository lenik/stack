package com.bee32.plover.restful.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.free.InputStreamSource;
import javax.free.NotImplementedException;
import javax.free.OutputStreamTarget;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ResourceDispatcher dispatcher;

    public DispatchServlet() {
        dispatcher = ResourceDispatcher.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        while (path.startsWith("/"))
            path = path.substring(1);

        IResource resource = dispatcher.dispatch(path);
        if (resource == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource: " + path);
            return;
        }

        InputStream in = resource.openBinary();
        OutputStream out = resp.getOutputStream();

        InputStreamSource source = new InputStreamSource(in);
        OutputStreamTarget target = new OutputStreamTarget(out);
        target.forWrite().writeBytes(source);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

}
