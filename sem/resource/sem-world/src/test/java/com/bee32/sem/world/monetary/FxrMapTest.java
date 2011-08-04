package com.bee32.sem.world.monetary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;

import com.bee32.sem.misc.i18n.ICurrencyAware;

public class FxrMapTest
        extends Assert
        implements ICurrencyAware {

    public static void main(String[] args) {
        FxrMap fm = new FxrMap(USD, FxrUsage.SELLING);
        fm.lazyLoad(parseDate("2008-1-1"), parseDate("2009-7-31"));
    }

    static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static Date parseDate(String s) {
        try {
            return DATE_FORMAT.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
