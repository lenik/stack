package com.bee32.sem.salary.util;

import java.util.Calendar;
import java.util.Date;

public class SalaryDateUtilTest {

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

    public static void main(String args[]) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int x = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(x);
    }

}
