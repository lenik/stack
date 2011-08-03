package com.bee32.sem.misc;

import java.util.Calendar;
import java.util.Date;

public class LocalDateUtil {

    public static Date truncate(Date date) {
        long time = date.getTime();
        time -= time % 86400000;
        return new Date(time);
    }

    public static int dateInt(Date date) {
        return (int) (date.getTime() / 86400000);
    }

    public static Date startOfTheNextDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date startOfTheDay(Date date) {
        return startOfTheNextDays(date, 0);
    }

    public static Date startOfTheNextDay(Date date) {
        return startOfTheNextDays(date, 1);
    }

}
