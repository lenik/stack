package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Map;

public class FxrTable {

    private static final long serialVersionUID = 1L;

    Currency unitCurrency;
    Map<Currency, FxrRecord> map;

    public FxrTable() {
    }

    public FxrTable(Currency unitCurrency) {
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        this.unitCurrency = unitCurrency;
    }

    public Currency getUnitCurrency() {
        return unitCurrency;
    }

    public void setUnitCurrency(Currency unitCurrency) {
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        this.unitCurrency = unitCurrency;
    }

    public FxrRecord getQuote(Currency quoteCurrency) {
        if (quoteCurrency == null)
            throw new NullPointerException("quoteCurrency");
        return map.get(quoteCurrency);
    }

    public FxrRecord putQuote(FxrRecord fxrRecord) {
        if (fxrRecord == null)
            throw new NullPointerException("fxrRecord");
        return map.put(fxrRecord.getQuoteCurrency(), fxrRecord);
    }

}
