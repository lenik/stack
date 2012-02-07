package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.html.PageDefMap;

public abstract class SimpleServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(SimpleServlet.class);

    protected final PageDefMap pages = new PageDefMap();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI(); // no params
        String pageName;
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash != -1)
            pageName = uri.substring(lastSlash + 1);
        else
            pageName = uri;

        logger.info(getPageHint() + ": " + pageName);

        IPageGenerator page = getPage(pageName);
        if (page == null)
            throw new ServletException("No such page: " + pageName);

        Map<String, ?> _args = req.getParameterMap();
        String content = page.generate(_args);

        resp.setContentType("text/html; charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.println(content);
    }

    protected String getPageHint() {
        return "Page";
    }

    public PageDefMap getPages() {
        return pages;
    }

    protected IPageGenerator getPage(String name) {
        return pages.getPage(name);
    }

}
