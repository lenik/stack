package com.bee32.sem.salary.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.free.Pair;

public class SalaryDateUtil {

    public static Pair<Date, Date> toDayRange(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        Date begin = cal.getTime();

        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date end = cal.getTime();

        return new Pair<Date, Date>(begin, end);
    }

    public static Pair<Date, Date> toMonthRange(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date begin = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        Date end = cal.getTime();

        return new Pair<Date, Date>(begin, end);
    }

    public static int getDayNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static Pair<Date, Date> getRecentlyRange(Pair<Date, Date> range) {
        Calendar cal = Calendar.getInstance();
        Date _from = range.get(0);
        cal.setTime(_from);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        Date from = cal.getTime();

        Date _to = range.get(1);
        cal.setTime(_to);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        Date to = cal.getTime();

        return new Pair<Date, Date>(from, to);
    }

    static DateFormat ZH_DATE = new SimpleDateFormat("yyyy年MM月");

    public static String getDateString(Date date) {
        return ZH_DATE.format(date);
    }

    public static String getDayString(Date date) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(date);
    }

    public static Pair<Integer, Integer> getYearAndMonth(Date date) {
        String tmp = getDateString(date);
        int year = Integer.parseInt(tmp.substring(0, 4));
        int month = Integer.parseInt(tmp.substring(5, 7));
        return new Pair<Integer, Integer>(year, month);
    }

}