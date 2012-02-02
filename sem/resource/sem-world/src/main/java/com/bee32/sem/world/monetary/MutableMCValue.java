package com.bee32.sem.world.monetary;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class MutableMCValue
        extends MCValue {

    private static final long serialVersionUID = 1L;

    Date fxrDate;

    public MutableMCValue() {
        super();
    }

    public MutableMCValue(BigDecimal value) {
        super(value);
    }

    public MutableMCValue(Currency currency, BigDecimal value) {
        super(currency, value);
    }

    public MutableMCValue(Currency currency, double value) {
        super(currency, value);
    }

    public MutableMCValue(Currency currency, int value) {
        super(currency, value);
    }

    public MutableMCValue(Currency currency, long value) {
        super(currency, value);
    }

    public MutableMCValue(double value) {
        super(value);
    }

    public MutableMCValue(int value) {
        super(value);
    }

    public MutableMCValue(long value) {
        super(value);
    }

    @Override
    public MutableMCValue clone() {
        MutableMCValue mmcv = new MutableMCValue(currency, value);
        mmcv.fxrDate = fxrDate;
        return mmcv;
    }

    @Override
    public MutableMCValue toMutable() {
        return this;
    }

    @Override
    public MCValue toImmutable() {
        return new MCValue(currency, value);
    }

    @Override
    public void setCurrency(Currency currency) {
        super.setCurrency(currency);
    }

    @Override
    public void setCurrencyCode(String currencyCode) {
        super.setCurrencyCode(currencyCode);
    }

    @Override
    public void setValue(BigDecimal value) {
        super.setValue(value);
    }

    public Date getFxrDate() {
        return fxrDate;
    }

    public void setFxrDate(Date fxrDate) {
        this.fxrDate = fxrDate;
    }

    public BigDecimal getNativeValue()
            throws FxrQueryException {
        return super.getNativeValue(fxrDate);
    }

    public void setNativeValue(BigDecimal nativeValue)
            throws FxrQueryException {
        IFxrProvider fxrProvider = FxrProviderFactory.getFxrProvider();

        float _fxr = fxrProvider.getFxr(fxrDate, currency, FxrUsage.MIDDLE);
        if (Float.isNaN(_fxr))
            throw new FxrQueryException("The FXR for the specified quote currency is not defined: " + currency);

        BigDecimal fxr = BigDecimal.valueOf(_fxr);
        if (fxr == BigDecimal.ZERO)
            throw new FxrQueryException(String.format("FXR for currency %s is zero available at %s.", //
                    getCurrencyText(), fxrDate));

        value = nativeValue.divide(fxr);
    }

}
