package com.bee32.sem.world.monetary;

import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.Dates;

import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.plover.util.i18n.CurrencyOrder;
import com.bee32.plover.util.i18n.ICurrencyAware;
import com.bee32.plover.util.i18n.ILocaleAware;

/**
 * 汇率表
 *
 * <p lang="en">
 * FXR Table
 */
public class FxrTable
        implements ICurrencyAware, ILocaleAware {

    // private static final long serialVersionUID = 1L;

    final Currency quoteCurrency;
    final Map<Currency, FxrRecord> unitMap;

    public FxrTable() {
        this(CurrencyConfig.getNative());
    }

    public FxrTable(Currency quoteCurrency) {
        if (quoteCurrency == null)
            throw new NullPointerException("quoteCurrency");
        this.quoteCurrency = quoteCurrency;

        unitMap = new TreeMap<Currency, FxrRecord>(CurrencyOrder.CODE);
    }

    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    public FxrRecord get(Currency unitCurrency) {
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        return unitMap.get(unitCurrency);
    }

    public FxrRecord put(FxrRecord fxrRecord) {
        if (fxrRecord == null)
            throw new NullPointerException("fxrRecord");
        Currency unitCurrency = fxrRecord.getUnitCurrency();
        return unitMap.put(unitCurrency, fxrRecord);
    }

    public Collection<FxrRecord> getRecords() {
        return unitMap.values();
    }

    public Date getFirstDate() {
        if (unitMap.isEmpty())
            return null;
        Entry<Currency, FxrRecord> first = unitMap.entrySet().iterator().next();
        Date date = first.getValue().getDate();
        return date;
    }

    @Override
    public String toString() {
        return "FXR-Table: " + Dates.YYYY_MM_DD.format(getFirstDate());
    }

    public String dump() {
        if (unitMap.isEmpty())
            return "(Empty FXR Table)";

        StringBuilder buf = new StringBuilder();

        buf.append("Date: " + Dates.YYYY_MM_DD.format(getFirstDate()) + "\n");
        buf.append("Quote-Currency: " + quoteCurrency + "\n");

        for (Entry<Currency, FxrRecord> entry : unitMap.entrySet()) {
            FxrRecord record = entry.getValue();

            String line = String.format("    [%s] %-5s BR=%4.4f BUY=%4.4f SEL=%4.4f\n", //
                    entry.getKey(), //
                    entry.getKey().getDisplayName(zh_CN), //
                    record.getBaseRate(), //
                    record.getBuyingRate(), //
                    record.getSellingRate());

            buf.append(line);
        }
        return buf.toString();
    }

}
