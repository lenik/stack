package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.plover.util.i18n.ICurrencyAware;

public abstract class AbstractFxrProvider
        extends DataService
        implements IFxrProvider, ICurrencyAware {

    protected FxrUsage usage = FxrUsage.MIDDLE;

    // @Override
    // public Currency getQuoteCurrency() {
    // return getLatestFxrTable().getQuoteCurrency();
    // }

    @Override
    public Currency getQuoteCurrency() {
        return CurrencyConfig.getNative();
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

    @Override
    public float getLatestFxr(Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException {
        return getFxr(new Date(), unitCurrency, usage);
    }

    static final int DEFAULT_TRACE_COUNT = 10;

    @Override
    public float getFxr(Date queryDate, Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException {
        if (queryDate == null)
            throw new NullPointerException("date");
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        if (usage == null)
            throw new NullPointerException("usage");

        IFxrMap fxrMap = getFxrMap(unitCurrency, usage);

        double rate = fxrMap.eval(queryDate);
        if (Double.isNaN(rate))
            // throw new IllegalStateException("Have not enough samples for interpolation.");
            return Float.NaN;

        return (float) rate;
    }

}
