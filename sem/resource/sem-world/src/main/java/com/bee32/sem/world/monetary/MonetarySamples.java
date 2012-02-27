package com.bee32.sem.world.monetary;

import java.text.ParseException;
import java.util.Currency;
import java.util.Date;

import javax.free.Dates;

import com.bee32.plover.orm.util.SampleList;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.plover.util.i18n.ICurrencyAware;

public class MonetarySamples
        extends NormalSamples
        implements ICurrencyAware {

    FxrRecord _(Date date, Currency unitCurrency, float buyingRate, float sellingRate, float baseRate) {
        FxrRecord record = new FxrRecord(date, unitCurrency, buyingRate, sellingRate, baseRate);
        return record;
    }

    @Override
    protected void getSamples(SampleList samples) {
        Date date;
        try {
            date = Dates.YYYY_MM_DD.parse("2011-7-22");
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        samples.add(_(date, codes.AUD, 6.9808f, 6.9667f, 7.0227f));
        samples.add(_(date, codes.CAD, 6.8224f, 6.8001f, 6.8547f));
        samples.add(_(date, codes.CHF, 7.8857f, 7.846f, 7.909f));
        samples.add(_(date, codes.DKK, 1.245f, 1.2412f, 1.2512f));
        samples.add(_(date, codes.EUR, 9.2815f, 9.2522f, 9.3265f));
        samples.add(_(date, codes.GBP, 10.5207f, 10.4738f, 10.558f));
        samples.add(_(date, codes.HKD, 0.8277f, 0.826f, 0.8292f));
        samples.add(_(date, codes.JPY, 0.082081f, 0.081714f, 0.08237f));
        samples.add(_(date, codes.MOP, 0.8038f, 0.8022f, 0.8053f));
        samples.add(_(date, codes.NOK, 1.1923f, 1.1909f, 1.2005f));
        samples.add(_(date, codes.NZD, 5.5425f, 5.5402f, 5.5847f));
        samples.add(_(date, codes.PHP, 0.1518f, 0.1514f, 0.1526f));
        samples.add(_(date, codes.RUB, 0.232f, 0.231f, 0.2329f));
        samples.add(_(date, codes.SEK, 1.0218f, 1.0205f, 1.0287f));
        samples.add(_(date, codes.SGD, 5.3285f, 5.3115f, 5.3542f));
        samples.add(_(date, codes.THB, 0.2157f, 0.2148f, 0.2165f));
        samples.add(_(date, codes.USD, 6.4495f, 6.4343f, 6.4601f));
    }

}
