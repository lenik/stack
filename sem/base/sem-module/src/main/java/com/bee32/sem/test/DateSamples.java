package com.bee32.sem.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import javax.free.Dates;

public class DateSamples {

    static DateFormat dateFormat;
    static DateFormat dateTimeFormat;

    static {
        TimeZone timeZone = TimeZone.getDefault();

        dateFormat = (DateFormat) Dates.YYYY_MM_DD.clone();
        dateFormat.setTimeZone(timeZone);
        dateTimeFormat = (DateFormat) Dates.dateTimeFormat.clone();
        dateTimeFormat.setTimeZone(timeZone);
    }

    protected static Date parseDate(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected static Date parseDateTime(String s) {
        try {
            return dateTimeFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static final Date D_2009_10_01 = parseDate("2009-10-1");

    public static final Date D_2010_01_01 = parseDate("2010-1-1");
    public static final Date D_2010_07_01 = parseDate("2010-7-1");
    public static final Date D_2010_07_02 = parseDate("2010-7-2");
    public static final Date D_2010_07_03 = parseDate("2010-7-3");
    public static final Date D_2010_07_10 = parseDate("2010-7-10");
    public static final Date D_2010_07_20 = parseDate("2010-7-20");
    public static final Date D_2010_07_30 = parseDate("2010-7-30");
    public static final Date D_2010_08_01 = parseDate("2010-8-1");
    public static final Date D_2010_08_31 = parseDate("2010-8-31");
    public static final Date D_2010_12_31 = parseDate("2010-12-31");

    public static final Date D_2011_01_01 = parseDate("2011-1-1");
    public static final Date D_2011_05_01 = parseDate("2011-5-1");

}
