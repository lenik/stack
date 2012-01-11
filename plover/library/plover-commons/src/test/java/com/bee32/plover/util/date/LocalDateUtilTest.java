package com.bee32.plover.util.date;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class LocalDateUtilTest
        extends Assert {

    @Test
    public void testMonthInt() {
        Date date = new Date();
        int monthInt = LocalDateUtil.monthIndex(date);
        System.out.println(monthInt);
    }

}
