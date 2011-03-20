package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.net.URL;

import javax.free.OutputStreamTarget;
import javax.free.URLResource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverlappedResourceServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(OverlappedResourceServlet.class);

    public OverlappedResourceServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        logger.debug("Get overlapped resource: " + path);

        if (path.startsWith("/"))
            path = path.substring(1);

        URL resourceUrl = OverlappedBases.searchResource(path);

        if (resourceUrl != null) {
            URLResource resource = new URLResource(resourceUrl);

            ServletOutputStream out = resp.getOutputStream();

            new OutputStreamTarget(out).forWrite().writeBytes(resource);
        }

        else
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    static URL searchClassInherited(Class<?> clazz, String path) {
        while (clazz != null) {

            URL resource = clazz.getResource(path);
            if (resource != null)
                return resource;

            clazz = clazz.getSuperclass();
        }

        return null;
    }

}
