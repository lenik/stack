package com.bee32.plover.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.free.Dates;

import org.junit.Assert;

public class TestSupport
        extends Assert {

    DateFormat dateFormat;
    DateFormat dateTimeFormat;

    public TestSupport() {
        dateFormat = (DateFormat) Dates.YYYY_MM_DD.clone();
        dateFormat.setTimeZone(getTimeZone());
        dateTimeFormat = (DateFormat) Dates.dateTimeFormat.clone();
        dateTimeFormat.setTimeZone(getTimeZone());
    }

    protected Locale getLocale() {
        return Locale.getDefault();
    }

    protected TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    protected Date parseDate(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected Date parseDateTime(String s) {
        try {
            return dateTimeFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected static void assertEqualsX(float expected, float actual) {
        float a = Math.abs(expected);
        float b = Math.abs(actual);
        float max = Math.max(a, b);
        float eps = max / 1e+6f;
        assertEquals(expected, actual, eps);
    }

    protected static void assertEqualsX(double expected, double actual) {
        double a = Math.abs(expected);
        double b = Math.abs(actual);
        double max = Math.max(a, b);
        double eps = max / 1e+13;
        assertEquals(expected, actual, eps);
    }

}
