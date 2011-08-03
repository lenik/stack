package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.world.math.InterpolatedMap;

public abstract class AbstractFxrProvider
        extends EnterpriseService
        implements IFxrProvider, ICurrencyAware {

    FxrUsage usage = FxrUsage.MIDDLE;

    // @Override
    // public Currency getQuoteCurrency() {
    // return getLatestFxrTable().getQuoteCurrency();
    // }

    @Override
    public Currency getQuoteCurrency() {
        return ICurrencyAware.NATIVE_CURRENCY;
    }

    @Override
    public FxrTable getFxrTable(Date date) {
        List<FxrTable> list = getFxrTableSeries(date, 1);
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public FxrTable getLatestFxrTable() {
        return getFxrTable(new Date());
    }

    static final int DEFAULT_TRACE_COUNT = 10;

    @Override
    public Float getFxr(Date queryDate, Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException {
        if (queryDate == null)
            throw new NullPointerException("date");
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        if (usage == null)
            throw new NullPointerException("usage");

        // OPT cache: date, unitCurrency, usage.

        List<FxrTable> tables = getFxrTableSeries(queryDate, DEFAULT_TRACE_COUNT);

        int intpMax = 3;
        InterpolatedMap intpMap = new InterpolatedMap();

        for (FxrTable table : tables) {
            FxrRecord record = table.get(unitCurrency);
            if (record == null)
                continue;

            Float rate = record.getRate(usage);
            if (rate == null)
                continue;

            intpMap.put(dateint(record.getDate()), (double) rate);

            if (intpMax-- <= 0)
                break;
        }

        if (intpMap.isEmpty())
            return null;

        Double intpRate = intpMap.get(dateint(queryDate));
        return intpRate.floatValue();
    }

    @Override
    public Float getLatestFxr(Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException {
        return getFxr(new Date(), unitCurrency, usage);
    }

    static int dateint(Date date) {
        return (int) (date.getTime() / 86400000);
    }

}
