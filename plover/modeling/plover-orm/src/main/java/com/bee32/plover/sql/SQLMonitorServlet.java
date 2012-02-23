package com.bee32.plover.sql;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.pp.para.GFmtOpt;
import gudusoft.gsqlparser.pp.para.GFmtOptFactory;
import gudusoft.gsqlparser.pp.stmtformattor.FormattorFactory;

import java.text.DateFormat;
import java.util.regex.Pattern;

import javax.free.Dates;
import javax.free.DisplayName;
import javax.free.Doc;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SimpleServlet;
import com.bee32.plover.site.SiteInstance;

public class SQLMonitorServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public SQLMonitorServlet() {
        pages.add("index", SQLLogs.class);
        pages.add("sql", SQLLogs.class);
    }

    static Pattern comments = Pattern.compile("/\\*.*?\\*/\\s*", Pattern.DOTALL);
    static TGSqlParser sqlParser = new TGSqlParser(EDbVendor.dbvpostgresql);
    static GFmtOpt sqlFormat = GFmtOptFactory.newInstance();

    @DisplayName("SQL Logs")
    @Doc("Recent SQL Logs")
    public static class SQLLogs
            extends HtmlTemplate {

        DateFormat timeFormat = Dates.HH_MM_SS;

        @Override
        protected void _pageContent() {
            SiteInstance site = ThreadHttpContext.getSiteInstance();
            SQLTrackDB sqlTrackDB = SQLTrackDB.getInstance(site);

            String format = getRequest().getParameter("format");

            table().border("1");
            {
                tr();
                {
                    td().text("Info").end();
                    td().text("SQL").end();
                    end();
                }
                // int i = 0;
                for (SQLRecord record : sqlTrackDB.getRecentSqls()) {
                    tr();
                    {
                        td().valign("top");
                        {
                            p().text("Source: " + record.source).end();
                            p().text("Connection-Id: " + record.connectionId).end();
                            p().text("Time: " + timeFormat.format(record.time)).end();
                            p().text("Elapsed: " + record.elapsed + "ms").end();
                            p().text("Category: " + record.category).end();
                        }
                        // td().text(record.prepared).end();
                        td().valign("top");
                        {
                            if ("1".equals(format)) {
                                sqlParser.sqltext = comments.matcher(record.sql).replaceAll("");
                                if (sqlParser.parse() != 0) {
                                    b().text("Failed to parse SQL: " + sqlParser.getErrormessage()).end();
                                    text(record.sql);
                                } else {
                                    String formatted = FormattorFactory.pp(sqlParser, sqlFormat);
                                    pre().text(formatted).end();
                                }
                                break;
                            } else {
                                text("[");
                                a().href("?format=1").text("VIEW").end();
                                text("] ");
                                text(record.sql);
                            }
                            end();
                        }
                        end();
                    }
                }
            }
            end();
        } // _pageContent

    } // Logs

}
