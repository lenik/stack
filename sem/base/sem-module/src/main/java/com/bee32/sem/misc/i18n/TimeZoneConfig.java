package com.bee32.sem.misc.i18n;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneConfig
        implements ITimeZoneAware, ILocaleAware {

    static TimeZone NATIVE = TimeZone.getDefault();

    public static TimeZone getNative() {
        return NATIVE;
    }

    public static void setNative(TimeZone _native) {
        NATIVE = _native;
    }

    public static List<TimeZone> list() {
        return list(TimeZoneOrder.OFFSET);
    }

    public static List<TimeZone> list(TimeZoneOrder order) {
        return order.list();
    }

    public static String format(TimeZone timeZone, Locale locale) {

        String id = timeZone.getID();

        int rawOffset = timeZone.getRawOffset() / 60000;
        int minutes = rawOffset % 60;
        int hours = rawOffset / 60;

        String name = timeZone.getDisplayName(locale);

        // [GMT+08:30] Asia/Shanghai (上海)
        StringBuilder sb = new StringBuilder();

        // [GMT..]
        sb.append("[GMT");
        if (hours >= 0)
            sb.append("+");
        if (hours < 10)
            sb.append('0');
        sb.append(hours);
        sb.append(':');
        if (minutes < 10)
            sb.append('0');
        sb.append(minutes);
        sb.append("] ");

        sb.append(id);
        sb.append(" (" + name + ")");

        return sb.toString();
    }

    public static String format(TimeZone timeZone) {
        Locale locale = NATIVE_LOCALE;
        return format(timeZone, locale);
    }

}
