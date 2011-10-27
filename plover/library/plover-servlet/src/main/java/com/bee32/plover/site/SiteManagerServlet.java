package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.free.Doc;
import javax.free.StringArray;
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
        pages.add(Config.class);
        pages.add(Create.class);
        pages.add(Delete.class);
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
            for (SiteInstance site : manager.getSites()) {
                String siteLabel = site.getLabel();
                String siteName = site.getName();

                div().style("background: #eef; margin-left: 1em; border-bottom: solid 1px #888;");
                {
                    h3();
                    text(" 应用：" + siteLabel);
                    a().href("config?site=" + siteName).text("[配置]").end();
                    end();

                    div().classAttr("tcf").style("margein-left: 1em;");
                    table().border("0");
                    {
                        tr().th().classAttr("key").text("代码：").end().td().classAttr("value").text(siteName).end(2);
                        tr().th().classAttr("key").text("域名绑定：").end().td().classAttr("value");
                        {
                            ol().style("margin: 0; padding: 0; list-style-type: none;");
                            for (String alias : site.getAliases()) {
                                li().text(alias).end();
                            }
                            end(3); // .tr.td.ol
                        }
                    }
                    end(2); // .div.table
                }
                end(); // .div
            }
        }

    }

    @Doc("配置应用")
    static class Config
            extends SiteTemplate {

        public Config(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content()
                throws Exception {
            SiteInstance site = getSiteInstance();
            boolean createSite = site == null;
            if (createSite)
                site = new SiteInstance();

            boolean save = args.getString("save") != null;

            if (save) {
                String label = args.getString("label");
                String aliases = args.getString("aliases");
                String dbHost = args.getString("dbhost");
                int dbPort = args.getInt("dbport");
                String dbUser = args.getString("dbuser");
                String dbPass = args.getString("dbpass");
                String dbName = args.getString("dbname");
                String _samples = args.getString("samples");
                SamplesSelection samples = SamplesSelection.valueOf(_samples);

                label = label.trim();
                Set<String> aliasSet = new LinkedHashSet<String>();
                for (String alias : aliases.split(",")) {
                    alias = alias.trim();
                    aliasSet.add(alias);
                }

                if (createSite) {
                    String siteName = args.getString("site");
                    site = siteManager.loadSite(siteName);
                }

                site.setProperty("label", label);
                site.setAliases(aliasSet);
                site.setDbHost(dbHost);
                site.setDbPort(dbPort);
                site.setDbUser(dbUser);
                site.setDbPass(dbPass);
                site.setDbName(dbName);
                site.setSamples(samples);

                site.saveConfig();

                if (createSite)
                    siteManager.addSite(site);
            }

            simpleForm("#", //
                    createSite ? "site" : "-site", "应用代码:应用的唯一代码，用于系统内部标识应用", site.getName(), //
                    "label", "标题:应用的显示名称，一般是企业名称", site.getLabel(), //
                    "description", "描述:应用的描述信息，如企业的全称", site.getDescription(), //
                    "aliases", "网络绑定:多个网络名称绑定，用逗号分隔", StringArray.join(", ", site.getAliases()), //
                    "dbhost", "数据服务器:数据库服务器的主机名称", site.getDbHost(), //
                    "dbport", "数据库端口:数据库服务器的端口号", site.getDbPort(), //
                    "dbuser", "数据库用户名:数据库的登录用户", site.getDbUser(), //
                    "dbpass", "数据库密码:数据库的登录密码", site.getDbPass(), //
                    "dbname", "数据库名称:数据库的名称", site.getDbName(), //
                    "samples", "样本加载:选择加载哪些样本", site.getSamples() //
            );

            fieldset().legend().text("删除该应用配置").end();
            simpleForm("delete:删除", //
                    "!removeData", "同时删除所有数据:危险：默认只删除配置文件，删除所有数据将无法恢复！", false //
            );
            end();

        }
    }

    @Doc("新建应用")
    static class Create
            extends Config {

        public Create(Map<String, ?> args) {
            super(args);
        }

    }

    @Doc("删除应用")
    static class Delete
            extends SiteTemplate {

        public Delete(Map<String, ?> _args) {
            super(_args);
        }

        @Override
        protected void _content()
                throws Exception {
            String confirmed = args.getString("confirmed");
            if ("1".equals(confirmed)) {
                a().href("index").text("应用已删除，点击返回列表");
            } else {
                fieldset().legend().text("确认删除").end();

                form().action("#");
                input().type("hidden").name("confirmed").value("1").end();
                input().type("submit").valign("删除");
                end(2); // .fieldset.form
            }
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
