package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bee32.sem.misc.i18n.CurrencyConfig;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.misc.i18n.ITimeZoneAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.FxrUsage;

public class DiscreteFxrProviderTest
        extends Assert
        implements ICurrencyAware, ITimeZoneAware {

    static {
        CurrencyConfig.setNative(CNY);
    }

    static DiscreteFxrProvider fxp = new DiscreteFxrProvider();
    static FxrSamplesSource source = new FxrSamplesSource();
    static Date latestDate = null;

    @Before
    public void testCommit()
            throws FxrQueryException, IOException {
        for (int i = 0; i < source.getCount(); i++) {
            FxrTable table = source.download();
            if (table != null) {
                fxp.commit(table);

                Date tableDate = table.getFirstDate();
                if (latestDate == null || latestDate.compareTo(tableDate) < 0)
                    latestDate = tableDate;
            }
        }
    }

    @Test
    public void testGetFxrTable_VeryEarly() {
        FxrTable table = fxp.getFxrTable(parseDate("2011-07-01"));
        assertNull(table);
    }

    @Test
    public void testGetFxrTable_Hit() {
        Date date = parseDate("2011-07-23");
        FxrTable table = fxp.getFxrTable(date);
        Date tableDate = table.getFirstDate();
        assertEquals(date, tableDate);
    }

    @Test
    public void testGetFxrTable_VeryLate() {
        Date date = parseDate("2055-01-01");
        FxrTable table = fxp.getFxrTable(date);
        Date tableDate = table.getFirstDate();
        assertEquals(latestDate, tableDate);
    }

    static final double epsilon = 1e-10;

    @Test
    public void testGetFxr_CNY()
            throws FxrQueryException {
        Date date1 = parseDate("2011-07-23");
        Date date2 = parseDate("2011-08-03");
        // int t1 = LocalDateUtil.dateInt(date1);
        // int t2 = LocalDateUtil.dateInt(date2);

        // 6.4313
        float usd1 = fxp.getFxr(date1, USD, FxrUsage.BUYING);
        // 6.4228
        float usd2 = fxp.getFxr(date2, USD, FxrUsage.BUYING);

        // 6.42705
        float expected = (usd1 + usd2) / 2;

        long midTime = (date1.getTime() + date2.getTime()) / 2;
        Date midDate = new Date(midTime); // 2011-7-28 12:00:00
        Float mid = fxp.getFxr(midDate, USD, FxrUsage.BUYING);

        assertEquals(expected, mid, epsilon);
    }

    static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    static {
        DATE_FORMAT.setTimeZone(TZ_PRC);
    }

    static Date parseDate(String s) {
        try {
            return DATE_FORMAT.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
