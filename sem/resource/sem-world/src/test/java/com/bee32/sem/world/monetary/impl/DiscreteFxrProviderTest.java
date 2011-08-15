package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.test.TestSupport;
import com.bee32.sem.misc.i18n.CurrencyConfig;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.misc.i18n.ITimeZoneAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.FxrUsage;

public class DiscreteFxrProviderTest
        extends TestSupport
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

    @Test
    public void testGetFxr_CNY()
            throws FxrQueryException {
        Date date1 = parseDate("2011-07-23");
        Date date2 = parseDate("2011-08-03");

        // 6.4313
        Float usd1 = fxp.getFxr(date1, USD, FxrUsage.BUYING);
        // 6.4228
        Float usd2 = fxp.getFxr(date2, USD, FxrUsage.BUYING);

        @SuppressWarnings("unused")
        float expected = (usd1 + usd2) / 2; // = 6.42705

        long midTime = (date1.getTime() + date2.getTime()) / 2;

        // here: int truncation loss
        Date midDate = new Date(midTime); // 2011-7-28 12:00:00

        // 6.426664
        float mid = fxp.getFxr(midDate, USD, FxrUsage.BUYING);

        // // 0.0007227
        // int t1 = LocalDateUtil.dayIndex(date1);
        // int t2 = LocalDateUtil.dayIndex(date2);
        // float eps = Math.abs((usd2 - usd1) / (t2 - t1));
        // assertEquals(expected, mid, eps);

        assertEqualsX(6.426664f, mid);
    }

}
