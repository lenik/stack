package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.free.Doc;
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
        pages.add(Index.class);
        pages.add(Create.class);
        pages.add(Data.class);
        pages.add(Cache.class);
        pages.add(Status.class);
        pages.add(HelpDoc.class);
    }

    @Doc("所有应用")
    static class Index
            extends SiteTemplate {

        public Index(Map<String, ?> _args) {
            super(_args);
        }

        @Override
        protected void _content() {
            SiteManager manager = SiteManager.getInstance();
            manager.getSites();
        }

    }

    @Doc("新建应用")
    static class Create
            extends SiteTemplate {

        public Create(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content() {
            SiteInstance site = getSiteInstance();

            boolean save = args.getString("save") != null;
            String name = args.getString("name");
            String label = args.getString("label");
            String aliases = args.getString("aliases");

            if (save) {
                name = name.trim();
                label = label.trim();
                Set<String> aliasSet = new LinkedHashSet<String>();
                for (String alias : aliases.split(",")) {
                    alias = alias.trim();
                    aliasSet.add(alias);
                }
                site.setName(name);
                site.setProperty("label", label);
                site.setAliases(aliasSet);
            }

            simpleForm("#", //
                    "name", "应用代码:应用的唯一代码，用于系统内部标识应用", name, //
                    "label", "标题:应用的显示名称，一般是企业名称", label, //
                    "aliases", "网络绑定:多个网络名称绑定，用逗号分隔", aliases //
            );
        }
    }

    @Doc("数据备份")
    static class Data
            extends SiteTemplate {

        public Data(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("缓存管理")
    static class Cache
            extends SiteTemplate {

        public Cache(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("运行状态")
    static class Status
            extends SiteTemplate {

        public Status(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("帮助信息")
    static class HelpDoc
            extends SiteTemplate {

        public HelpDoc(Map<String, ?> args) {
            super(args);
        }

    }

}
