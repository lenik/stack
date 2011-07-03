package com.bee32.plover.servlet.rabbits;

import java.io.IOException;
import java.net.URL;

import javax.free.OutputStreamTarget;
import javax.free.StringPart;
import javax.free.URLResource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logo
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(Logo.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // logger.debug("Logo servlet: " + req.getRequestURI());

        String base = StringPart.afterLast(req.getRequestURI(), '/');

        URL url = getClass().getResource("logo/" + base);
        if (url == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        URLResource resource = new URLResource(url);

        ServletOutputStream out = resp.getOutputStream();
        new OutputStreamTarget(out).forWrite().writeBytes(resource);

        out.close();
    }

}
