package com.bee32.plover.util.date;

import java.util.Calendar;
import java.util.Date;

public class LocalDateUtil {

    public static Date truncate(Date date) {
        long time = date.getTime();
        time -= time % 86400000;
        return new Date(time);
    }

    public static int dayIndex(Date date) {
        return (int) (date.getTime() / 86400000);
    }

    public static int monthIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR); // year = "2011"
        int month = cal.get(Calendar.MONTH); // JANUARY = 0
        return year * 12 + month;
    }

    public static Date beginOfMonthIndex(int monthIndex) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, monthIndex / 12);
        cal.set(Calendar.MONTH, monthIndex % 12);
        return cal.getTime();
    }

    public static Date endOfMonthIndex(int monthIndex) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, monthIndex / 12);
        cal.set(Calendar.MONTH, monthIndex % 12 + 1);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date beginOfTheNextDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date endOfTheNextDays(Date date, int days) {
        Date begin = beginOfTheNextDays(date, days + 1);
        Date prev = new Date(begin.getTime() - 1);
        return prev;
    }

    public static Date beginOfTheDay(Date date) {
        return beginOfTheNextDays(date, 0);
    }

    public static Date endOfTheDay(Date date) {
        return endOfTheNextDays(date, 0);
    }

}
