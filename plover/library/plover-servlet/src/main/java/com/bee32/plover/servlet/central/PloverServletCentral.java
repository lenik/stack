package com.bee32.plover.servlet.central;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.servlet.util.ModifiableHttpServletRequest;

public class PloverServletCentral
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Map<String, HttpServlet> servletMap;

    public PloverServletCentral() {
        servletMap = new HashMap<String, HttpServlet>();
        reload();
    }

    void reload() {
        servletMap.clear();
        for (PloverServlet servlet : ServiceLoader.load(PloverServlet.class)) {
            String name = servlet.getName();
            servletMap.put(name, servlet);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        assert pathInfo.startsWith("/");

        String mod;
        int nextSlash = pathInfo.indexOf('/', 1);
        if (nextSlash == -1) {
            mod = pathInfo.substring(1);
            pathInfo = "/";
        } else {
            mod = pathInfo.substring(1, nextSlash);
            pathInfo = pathInfo.substring(nextSlash);
        }

        HttpServlet servlet = servletMap.get(mod);
        if (servlet == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ModifiableHttpServletRequest mreq = new ModifiableHttpServletRequest(req);
        mreq.setPathInfo(pathInfo);

        servlet.service(mreq, resp);
    }

}
