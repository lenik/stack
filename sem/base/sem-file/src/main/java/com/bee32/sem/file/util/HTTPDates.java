package com.bee32.sem.file.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HTTPDates {

    static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1123 format.
     */
    public static final DateFormat RFC1123 = new SimpleDateFormat(PATTERN_RFC1123);

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1036 format.
     */
    public static final DateFormat RFC1036 = new SimpleDateFormat(PATTERN_RFC1036);

    /**
     * Date format pattern used to parse HTTP date headers in ANSI C <code>asctime()</code> format.
     */
    public static final DateFormat ASCTIME = new SimpleDateFormat(PATTERN_ASCTIME);

}
