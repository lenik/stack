package com.bee32.sem.salary.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.free.Pair;

public class SalaryDateUtil {

    /**
     * 返回一个（天）时间段Pair<Date,Date>(begin, end)
     */
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

    /**
     * 返回一个（月）时间段Pair<Date,Date>(begin, end)
     */
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

    /**
     * 是一个月的第几天 return Calendar.DAY_OF_MONTH
     */
    public static int getDayNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * return Calender.MONTH +1
     */
    public static int getMonNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得最近一个月的时间段
     */
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

    /**
     * 取得 雇员受雇到现在的月份
     */
    public static int getFixedNumberOfYears(Date begin) {
        Calendar c_begin = Calendar.getInstance();
        Calendar c_now = Calendar.getInstance();
        c_begin.setTime(begin);
        int year_begin = c_begin.get(Calendar.YEAR);
        int month_begin = c_begin.get(Calendar.MONTH);
        int year_now = c_now.get(Calendar.YEAR);
        int month_now = c_now.get(Calendar.MONTH);
        return (year_now - year_begin) * 12 + month_now - month_begin;
    }

    /**
     * 取的一个月的天数
     */
    public static int getDayNumberOfMonth(Date date) {
        Pair<Date, Date> range = toMonthRange(date);
        long x = range.getSecond().getTime() - range.getFirst().getTime();
        return (int) (x / 86400000);
    }

    public static int getDayNumberOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        Date date = cal.getTime();
        return getDayNumberOfMonth(date);
    }

    /**
     * 根据年月日返回日期
     */
    public static Date convertToDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static int convertToYearMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return year * 100 + month;
    }

    /**
     * 返回以2011年5月1号的参照天数，和这个月的周数
     */
    public static Pair<Integer, Integer> getReffer(int yearMonth) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearMonth / 100);
        cal.set(Calendar.MONTH, yearMonth % 100 - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date start = cal.getTime();

        Calendar refered = Calendar.getInstance();
        refered.set(2011, 4, 1); // Sunday
        refered.set(Calendar.HOUR_OF_DAY, 0);
        refered.set(Calendar.MINUTE, 0);
        refered.set(Calendar.MILLISECOND, 0);
        Date referDate = refered.getTime();

        long x = start.getTime() - referDate.getTime();

        int actualMaximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, actualMaximum);
        int week = cal.get(Calendar.WEEK_OF_MONTH);

        int refer = (int) (x / 86400000 % 7);

        return new Pair<Integer, Integer>(refer, week);
    }
}