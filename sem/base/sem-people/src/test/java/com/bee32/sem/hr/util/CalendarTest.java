package com.bee32.sem.hr.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        GregorianCalendar   calendar1=new   GregorianCalendar();
        GregorianCalendar   calendar2=new   GregorianCalendar();

        calendar1.set(Calendar.YEAR, 2007);
        calendar1.set(Calendar.MONTH, 7);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        calendar2.set(Calendar.YEAR, 2011);
        calendar2.set(Calendar.MONTH, 5);
        calendar2.set(Calendar.DAY_OF_MONTH, 10);

        int   monthgap=calendar2.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH);//月差
        int   daygap=calendar2.get(Calendar.DAY_OF_YEAR)-calendar1.get(Calendar.DAY_OF_YEAR);//日差

        System.out.println("\nmonthgap:" + monthgap);
        System.out.println("\ndaygap:" + daygap);

    }

}
