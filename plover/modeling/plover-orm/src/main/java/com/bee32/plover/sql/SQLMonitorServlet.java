package com.bee32.plover.sql;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.pp.para.GFmtOpt;
import gudusoft.gsqlparser.pp.para.GFmtOptFactory;
import gudusoft.gsqlparser.pp.stmtformattor.FormattorFactory;

import java.text.DateFormat;

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
        pages.add(Logs.class);
    }

    static TGSqlParser sqlParser = new TGSqlParser(EDbVendor.dbvpostgresql);
    static GFmtOpt sqlFormat = GFmtOptFactory.newInstance();

    @DisplayName("SQL Logs")
    @Doc("Recent SQL Logs")
    public static class Logs
            extends HtmlTemplate {

        DateFormat timeFormat = Dates.HH_MM_SS;

        @Override
        protected void _pageContent() {
            SiteInstance site = ThreadHttpContext.getSiteInstance();
            SQLTrackDB sqlTrackDB = SQLTrackDB.getInstance(site);

            table().border("1");
            {
                tr();
                {
                    td().text("Connection").end();
                    td().text("Time").end();
                    td().text("Elapsed").end();
                    td().text("Category").end();
                    td().text("SQL").end();
                    end();
                }
                for (SQLRecord record : sqlTrackDB.getRecentSqls()) {
                    tr();
                    {
                        td().valign("top").text(String.valueOf(record.connectionId)).end();
                        td().valign("top").text(timeFormat.format(record.time)).end();
                        td().valign("top").text(record.elapsed + "ms").end();
                        td().valign("top").text(record.category).end();
                        // td().text(record.prepared).end();
                        td().valign("top");
                        {
                            sqlParser.sqltext = record.sql;
                            if (sqlParser.parse() != 0) {
                                b().text("Failed to parse SQL: " + sqlParser.getErrormessage()).end();
                                text(record.sql);
                            } else {
                                String formatted = FormattorFactory.pp(sqlParser, sqlFormat);
                                pre().text(formatted).end();
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
