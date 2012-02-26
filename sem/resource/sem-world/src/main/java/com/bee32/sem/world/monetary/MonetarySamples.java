package com.bee32.sem.world.monetary;

import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;

import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.plover.util.i18n.ICurrencyAware;

public class MonetarySamples
        extends SampleContribution
        implements ICurrencyAware {

    @Override
    protected void listSamples() {
        Date date;
        try {
            date = Dates.YYYY_MM_DD.parse("2011-7-22");
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        add(new FxrRecord(date, codes.AUD, 6.9808f, 6.9667f, 7.0227f));
        add(new FxrRecord(date, codes.CAD, 6.8224f, 6.8001f, 6.8547f));
        add(new FxrRecord(date, codes.CHF, 7.8857f, 7.846f, 7.909f));
        add(new FxrRecord(date, codes.DKK, 1.245f, 1.2412f, 1.2512f));
        add(new FxrRecord(date, codes.EUR, 9.2815f, 9.2522f, 9.3265f));
        add(new FxrRecord(date, codes.GBP, 10.5207f, 10.4738f, 10.558f));
        add(new FxrRecord(date, codes.HKD, 0.8277f, 0.826f, 0.8292f));
        add(new FxrRecord(date, codes.JPY, 0.082081f, 0.081714f, 0.08237f));
        add(new FxrRecord(date, codes.MOP, 0.8038f, 0.8022f, 0.8053f));
        add(new FxrRecord(date, codes.NOK, 1.1923f, 1.1909f, 1.2005f));
        add(new FxrRecord(date, codes.NZD, 5.5425f, 5.5402f, 5.5847f));
        add(new FxrRecord(date, codes.PHP, 0.1518f, 0.1514f, 0.1526f));
        add(new FxrRecord(date, codes.RUB, 0.232f, 0.231f, 0.2329f));
        add(new FxrRecord(date, codes.SEK, 1.0218f, 1.0205f, 1.0287f));
        add(new FxrRecord(date, codes.SGD, 5.3285f, 5.3115f, 5.3542f));
        add(new FxrRecord(date, codes.THB, 0.2157f, 0.2148f, 0.2165f));
        add(new FxrRecord(date, codes.USD, 6.4495f, 6.4343f, 6.4601f));
    }

}
