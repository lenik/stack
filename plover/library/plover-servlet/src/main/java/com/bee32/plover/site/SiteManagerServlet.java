package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.free.Doc;
import javax.free.Order;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteManagerServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI(); // no params
        String cmdname;
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash != -1)
            cmdname = uri.substring(lastSlash + 1);
        else
            cmdname = uri;

        IPageGenerator page = pages.getPage(cmdname);
        if (page == null)
            throw new ServletException("Bad command: " + cmdname);

        Map<String, ?> _args = req.getParameterMap();
        String content = page.generate(_args);

        PrintWriter out = resp.getWriter();
        out.println(content);
    }

    static PageMap pages;
    static {
        pages = new PageMap();
        pages.add("index", -1, new SiteIndex(pages));
        pages.add(About.class);
        pages.add(Create.class);
    }

    @Order(100)
    @Doc("关于")
    static class About
            extends SiteTemplate {

        public About(Map<String, ?> args) {
            super(args);
        }

    }

    @Doc("新建实例")
    static class Create
            extends SiteTemplate {

        public Create(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content() {
            SiteInstance site = getSiteInstance();

            // if `label` is provided, set it anyway.
            String label = args.getString("label");
            if (label != null) {
                label = label.trim();
                site.setProperty("label", label);
            }

            simpleForm("#", "label", "Label", label);
        }

    }

}
