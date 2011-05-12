package com.bee32.plover.util.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DayPartitionTest {

    static DayPartition DP = new DayPartition(5 * 60, 12 * 60, 18 * 60);
    static {
        DP.parts[0].setName("MORNING");
        DP.parts[1].setName("AFTERNOON");
        DP.parts[2].setName("EVENING");
    }

    static Date _(int year, int month, int day, int hr, int min) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(year, month, day, hr, min);
        return cal.getTime();
    }

    @Test
    public void test() {
        Date from = _(2001, 11, 3, 5, 0);
        Date to = _(2001, 11, 5, 10, 3);
        for (DateWithDayPart d : DP.iterate(from, to))
            System.out.println(d);
    }

}
