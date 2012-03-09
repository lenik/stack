package com.bee32.plover.thirdparty.p6spy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.sql.SQLRecord;
import com.bee32.plover.sql.SQLTrackDB;
import com.bee32.plover.util.i18n.ITimeZoneAware;
import com.p6spy.engine.logging.appender.FormattedLogger;
import com.p6spy.engine.logging.appender.P6Logger;

public class PloverP6spySlf4jLogger
        extends FormattedLogger
        implements P6Logger {

    static Logger logger = LoggerFactory.getLogger("p6spy");

    static long bootTime = System.currentTimeMillis();

    static DateFormat timeFormat;
    static {
        timeFormat = new SimpleDateFormat("HH:mm:ss.MM");
        timeFormat.setTimeZone(ITimeZoneAware.TZ_GMT);
    }

    /**
     * Original Format: 1315265848160|63|1|statement||select relname from pg_class where relkind='S'
     */
    @Override
    public void logSQL(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        StringBuilder sb = new StringBuilder(500);

        long _time = 0;
        String time;
        try {
            _time = Long.parseLong(now);
            time = timeFormat.format(_time - bootTime);
        } catch (Exception e) {
            e.printStackTrace();
            time = "Error:" + e.getMessage();
        }

        // 00:02:14.431 63ms
        sb.append(time);
        sb.append(' ');
        sb.append(elapsed);
        sb.append("ms");

        if (connectionId != -1) {
            sb.append(" Con");
            sb.append(connectionId);
        }

        if (!"statement".equals(category)) {
            sb.append('|');
            sb.append(category);
        }

        String appfn = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement frame : stackTrace) {
            String fqcn = frame.getClassName();
            if (fqcn.contains("$$")) // skip proxies.
                continue;
            if (fqcn.startsWith("com.bee32.sem.") || fqcn.startsWith("com.bee32.icsf.")) {
                int dot = fqcn.lastIndexOf('.');
                String simple = fqcn.substring(dot + 1);
                String fn = frame.getMethodName();
                appfn = simple + "." + fn + ":" + frame.getLineNumber();
                break;
            }
        }
        if (appfn != null)
            sb.append("[" + appfn + "]");

        sb.append(": ");

        sql = sql.replace('\n', ' ').trim();
        sb.append(sql);

        String text = sb.toString();
        logText(text);

        // The fallback site should be enabled.
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        SQLTrackDB trackDB = SQLTrackDB.getInstance(site);
        SQLRecord sqlRecord = new SQLRecord(appfn, connectionId, _time, elapsed, category, prepared, sql);
        trackDB.addSql(sqlRecord);
    }

    @Override
    public void logText(String text) {
        logger.info(text);
    }

    @Override
    public void logException(Exception e) {
        logger.error(e.getMessage(), e);
    }

}
