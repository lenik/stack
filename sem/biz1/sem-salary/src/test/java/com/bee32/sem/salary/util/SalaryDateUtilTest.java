package com.bee32.sem.salary.util;

import java.util.Calendar;
import java.util.Date;

public class SalaryDateUtilTest {
    public static void main(String args[]) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int x = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(x);
    }
}
