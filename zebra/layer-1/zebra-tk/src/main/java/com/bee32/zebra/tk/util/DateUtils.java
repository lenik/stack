package com.bee32.zebra.tk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.bodz.lily.model.base.IMomentInterval;

public class DateUtils {

    static DateFormat yyyyMMdd_zh = new SimpleDateFormat("yyyy年MM月dd日");

    public static String formatRange(IMomentInterval o, String nullStart, String nullEnd) {
        return formatRange(o.getBeginDate(), o.getEndDate(), nullStart, nullEnd);
    }

    public static String formatRange(Date start, Date end, String nullStart, String nullEnd) {
        DateFormat dateFormat = yyyyMMdd_zh;
        String s1 = start == null ? nullStart : dateFormat.format(start);
        String s2 = end == null ? nullEnd : dateFormat.format(end);
        return s1 + " - " + s2;
    }

}
