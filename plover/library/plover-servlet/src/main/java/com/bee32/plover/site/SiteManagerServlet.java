package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.free.Doc;
import javax.free.StringArray;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.html.PageDefMap;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.cfg.DBAutoDDL;
import com.bee32.plover.site.cfg.DBDialect;
import com.bee32.plover.site.cfg.OptimizationLevel;
import com.bee32.plover.site.cfg.SamplesSelection;
import com.bee32.plover.site.cfg.VerboseLevel;
import com.bee32.plover.site.scope.SiteNaming;

public class SiteManagerServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(SiteManagerServlet.class);

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

        logger.info("Site-Command: " + cmdname);

        IPageGenerator page = pageDefs.getPage(cmdname);
        if (page == null)
            throw new ServletException("Bad command: " + cmdname);

        Map<String, ?> _args = req.getParameterMap();
        String content = page.generate(_args);

        resp.setContentType("text/html; charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.println(content);
    }

    static PageDefMap pageDefs;
    static {
        pageDefs = new PageDefMap();
        pageDefs.add(Index.class);
        pageDefs.add(CurrentSite.class);
        pageDefs.add(Config.class);
        pageDefs.add(Create.class);
        pageDefs.add(Delete.class);
        pageDefs.add(Reload.class);
        pageDefs.add(DataMaintainance.class);
        pageDefs.add(CacheManager.class);
        pageDefs.add(Monitor.class);
        pageDefs.add(HelpDoc.class);
    }

    @Doc("所有站点")
    public static class Index
            extends SiteTemplate {

        public Index(Map<String, ?> _args) {
            super(_args);
        }

        @Override
        protected void _content() {
            SiteManager manager = SiteManager.getInstance();
            for (SiteInstance site : manager.getSites()) {
                String name = site.getName();
                String label = site.getLabel();
                String description = site.getDescription();
                Location logo = site.getLogoLocation();

                div().style("background: #eef; margin-left: 1em; border-bottom: solid 1px #888;");
                {
                    h3();
                    text("站点：" + label);
                    a().href("config?site=" + name).text("配置").end();
                    a().href("reload?site=" + name).text("更新").end();
                    end();

                    div().classAttr("tcf").style("margein-left: 1em;");
                    table().border("0");
                    {
                        simpleRow("代码：", name);
                        if (description != null)
                            simpleRow("描述：", description);
                        if (logo != null)
                            simpleRowImage("徽标：", logo, null);
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

    @Doc("当前站点")
    public static class CurrentSite
            extends SiteTemplate {

        public CurrentSite(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content()
                throws Exception {
            SiteInstance site;
            if (this.site == null)
                site = ThreadHttpContext.getSiteInstance();
            else
                site = this.site;

            fieldset().style("text-align: center;");
            if (site == null) {
                legend().text("站点不可用").end();
                p().text("当前站点尚未配置。").end();
            } else {
                legend().text("站点：" + site.getLabel()).end();

                String _img = site.getLogoLocation().resolveAbsolute(request);
                img().classAttr("icon-big").src(_img).end();

                p().style("font-size: 200%").text(site.getDescription()).end();
            }
            end();

            String siteAlias = SiteNaming.getSiteAlias(request);
            fieldset();
            {
                legend().text("Site for the request: (alias=" + siteAlias + ")").end();
                Enumeration<String> headerNames = request.getHeaderNames();
                table();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    tr();
                    th().text(headerName).end();
                    td().text(headerValue).end();
                    end();
                }
                end();
            }
            end();

            if (site == null)
                return;

            // Detail config goes here...
        }

    }

    @Doc("配置站点")
    static class Config
            extends SiteTemplate {

        public Config(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content()
                throws Exception {
            boolean createSite = site == null;
            if (createSite)
                site = new SiteInstance();

            String name = args.getNString("site");
            boolean save = args.getString("save") != null;

            if (save) {
                String label = args.getNString("label");
                String description = args.getNString("description");
                String _logo = args.getNString("logo");
                String _theme = args.getNString("theme");
                String _verbose = args.getString("verbose");
                String _optimization = args.getString("opt");
                String aliases = args.getString("aliases");
                String _dialect = args.getString("dialect");
                String url = args.getNString("url");
                String dbName = args.getNString("dbname");
                String dbUser = args.getNString("dbuser");
                String dbPass = args.getNString("dbpass");
                String _autoddl = args.getString("autoddl");
                String _samples = args.getString("samples");

                if (label == null)
                    label = name;
                if (dbName == null)
                    dbName = name + "_db";

                Set<String> aliasSet = new LinkedHashSet<String>();
                for (String alias : aliases.split(",")) {
                    alias = alias.trim();
                    aliasSet.add(alias);
                }

                VerboseLevel verbose = VerboseLevel.valueOf(_verbose);
                OptimizationLevel optimization = OptimizationLevel.valueOf(_optimization);

                DBDialect dialect = DBDialect.valueOf(_dialect);
                DBAutoDDL autoddl = DBAutoDDL.valueOf(_autoddl);
                SamplesSelection samples = SamplesSelection.valueOf(_samples);
                if (url == null)
                    url = dialect.getUrlFormat();

                if (createSite)
                    site = manager.loadSiteConfig(name);

                site.setLabel(label);
                site.setDescription(description);
                site.setLogo(_logo);
                site.setTheme(_theme);
                site.setVerboseLevel(verbose);
                site.setOptimizationLevel(optimization);
                site.setAliases(aliasSet);
                site.setDbDialect(dialect);
                site.setDbUrlFormat(url);
                site.setDbName(dbName);
                site.setDbUser(dbUser);
                site.setDbPass(dbPass);
                site.setAutoDDL(autoddl);
                site.setSamples(samples);

                site.saveConfig();

                if (createSite)
                    manager.loadSite(site);

                p().text("保存成功。[" + System.identityHashCode(this) + "]").end();
            }

            simpleForm("#", //
                    (createSite && !save) ? "site" : "-site", "站点代码:站点的唯一代码，用于系统内部标识站点", name, //
                    "label", "标题:站点的显示名称，一般是企业名称", site.getLabel(), //
                    "description", "描述:应用的描述信息，如企业的全称", site.getDescription(), //
                    "logo", "徽标:站点的图标，如公司徽标", site.getLogoLocation(), //
                    "theme", "风格:站点的首选风格", site.getTheme(), //
                    "verbose", "调试级别:输出的调试信息的级别", site.getVerboseLevel(), //
                    "opt", "优化级别:设置缓存等优化支持的级别", site.getOptimizationLevel(), //
                    "aliases", "网络绑定:多个网络名称绑定，用逗号分隔", StringArray.join(", ", site.getAliases()), //
                    "dialect", "数据库类型:数据库的厂商类型", site.getDbDialect(), //
                    "url", "连接地址:数据库的JDBC连接地址（%s用于替换数据库名）", site.getDbUrlFormat(), //
                    "dbname", "数据库名称:数据库的名称", site.getDbName(), //
                    "dbuser", "数据库用户名:数据库的登录用户", site.getDbUser(), //
                    "dbpass", "数据库密码:数据库的登录密码", site.getDbPass(), //
                    "autoddl", "DDL模式:数据库自动创建DDL的模式", site.getAutoDDL(), //
                    "samples", "样本加载:选择加载哪些样本", site.getSamples() //
            );

            if (!createSite) {
                fieldset().legend().text("删除该应用配置").end();
                simpleForm("delete:删除", //
                        ".site", "站点代码:站点的唯一代码，用于系统内部标识应用", site.getName(), //
                        "!removeData", "同时删除所有数据:危险：默认只删除配置文件，删除所有数据将无法恢复！", false //
                );
                end();
            }

        }
    }

    @Doc("新建站点")
    public static class Create
            extends Config {

        public Create(Map<String, ?> args) {
            super(args);
        }

    }

    @Doc("删除站点")
    static class Delete
            extends SiteTemplate {

        public Delete(Map<String, ?> _args) {
            super(_args);
        }

        @Override
        protected void _content()
                throws Exception {
            String removeData = args.getString("removeData");
            String confirmed = args.getString("confirmed");

            if ("1".equals(confirmed)) {
                if (site == null) {
                    a().href("index").text("站点不存在或已经被删除，点击返回列表").end();
                } else {
                    manager.deleteSite(site);
                    a().href("index").text("站点已删除，点击返回列表").end();
                }
            } else {
                fieldset().legend().text("确认删除").end();

                form().action("#");
                input().type("hidden").name("site").value(site.getName()).end();

                p().text("您确定要删除站点 " + site.getLabel() + " 吗？").end();
                input().type("hidden").name("confirmed").value("1").end();

                if ("1".equals(removeData)) {
                    input().type("hidden").name("removeData").valign("1").end();
                    p().text("附件：同时删除所有数据").end();
                }
                input().type("submit").value("删除");
                end(2); // .fieldset.form
            }
        }
    }

    @Doc("更新站点配置")
    static class Reload
            extends SiteTemplate {

        public Reload(Map<String, ?> _args) {
            super(_args);
        }

        @Override
        protected void _content()
                throws Exception {
            p().text("重新读取配置文件……").end();

            site.reloadConfig();

            p().text("更新完成。").end();

            a().href("index").text("返回站点列表").end();
        }

    }

    @Doc("数据备份")
    public static class DataMaintainance
            extends SiteTemplate {

        public DataMaintainance(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("缓存管理")
    public static class CacheManager
            extends SiteTemplate {

        public CacheManager(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("站点监控")
    public static class Monitor
            extends SiteTemplate {

        public Monitor(Map<String, ?> _args) {
            super(_args);
        }

    }

    @Doc("帮助信息")
    public static class HelpDoc
            extends SiteTemplate {

        public HelpDoc(Map<String, ?> args) {
            super(args);
        }

        @Override
        protected void _content()
                throws Exception {
            h3().text("智恒站点管理程序。").end();
            p().text("本程序用于创建、配置、监控一台服务器上运行的所有站点。").end();
        }

    }

}
