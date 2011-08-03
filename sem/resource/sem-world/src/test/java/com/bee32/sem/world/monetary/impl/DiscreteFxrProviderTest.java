package com.bee32.sem.world.monetary.impl;

import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.world.monetary.CurrencyConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.ICurrencyAware;

public class DiscreteFxrProviderTest
        extends Assert
        implements ICurrencyAware {

    DiscreteFxrProvider fxp = new DiscreteFxrProvider();
    FxrSamplesSource source = new FxrSamplesSource();

    static {
        CurrencyConfig.setNative(CNY);
    }

    @Test
    public void testCommit()
            throws FxrQueryException {

        for (FxrTable table : source)
            fxp.commit(table);

        FxrTable table = fxp.getFxrTable(parseDate("2011-07-23"));
        System.out.println(table);
    }

    static Date parseDate(String s) {
        try {
            return Dates.YYYY_MM_DD.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
