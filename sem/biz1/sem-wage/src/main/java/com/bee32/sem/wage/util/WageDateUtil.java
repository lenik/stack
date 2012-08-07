package com.bee32.sem.wage.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WageDateUtil {

    public static Date getTestDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 6);
        return cal.getTime();
    }

    public static Map<Integer, Date> toDayRange(Date date) {
        Map<Integer, Date> map = new HashMap<Integer, Date>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        map.put(0, cal.getTime());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        map.put(1, cal.getTime());
        return map;
    }

    public static Map<Integer, Date> toMonthRange(Date date) {
        Map<Integer, Date> map = new HashMap<Integer, Date>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        map.put(0, cal.getTime());

        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        map.put(1, cal.getTime());
        return map;
    }

    public static double getRefferDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        Date base = cal.getTime();

        return (date.getTime() - base.getTime()) / 86400000;
    }

    public static int getDayNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Map<Integer, Date> getRecentlyRange(Map<Integer, Date> rangeMap) {
        Calendar cal = Calendar.getInstance();
        Date from = rangeMap.get(0);
        cal.setTime(from);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        rangeMap.put(0, cal.getTime());
        Date to = rangeMap.get(1);
        cal.setTime(to);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        rangeMap.put(1, cal.getTime());
        return rangeMap;
    }
}