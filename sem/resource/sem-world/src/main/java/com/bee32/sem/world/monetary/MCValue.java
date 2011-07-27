package com.bee32.sem.world.monetary;

import static com.bee32.plover.orm.ext.config.DecimalConfig.MONEY_ITEM_PRECISION;
import static com.bee32.plover.orm.ext.config.DecimalConfig.MONEY_ITEM_SCALE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.config.DecimalConfig;

/**
 * Multi-currency value, or multi-currency amount.
 *
 * @see
 * @see http://stackoverflow.com/questions/331744/jpa-multiple-embedded-fields
 */
@Embeddable
public class MCValue
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Currency DEFAULT_CURRENCY;
    static {
        Locale defaultLocale = Locale.getDefault();
        DEFAULT_CURRENCY = Currency.getInstance(defaultLocale);
    }

    private/* final */Currency currency = DEFAULT_CURRENCY;
    private/* final */BigDecimal value;

    public MCValue() {
        value = new BigDecimal(0);
    }

    public MCValue(MCValue mcv) {
        if (mcv == null)
            throw new NullPointerException("mcv");
        currency = mcv.currency;
        value = mcv.value;
    }

    public MCValue(Currency currency, int value) {
        this(currency, new BigDecimal(value, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, long value) {
        this(currency, new BigDecimal(value, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, double value) {
        this(currency, new BigDecimal(value, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.currency = currency;
        this.value = value;
    }

    /**
     * Get the currency.
     */
    @Transient
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Set the currency.
     */
    void setCurrency(Currency currency) {
        if (currency == null)
            throw new NullPointerException("currency");
        this.currency = currency;
    }

    @Column(name = "currency", length = 3/* , nullable = false */)
    public String getCurrencyCode() {
        // if (currency == null)
        // return null;
        return currency.getCurrencyCode();
    }

    void setCurrencyCode(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode); // Already throws IAE, though.
        if (currency == null)
            throw new IllegalArgumentException("Bad currency code: " + currencyCode);
        setCurrency(currency);
    }

    public MCValue currency(Currency currency) {
        return new MCValue(currency, value);
    }

    public MCValue currency(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode); // Already throws IAE, though.
        if (currency == null)
            throw new IllegalArgumentException("Bad currency code: " + currencyCode);
        return new MCValue(currency, value);
    }

    public MCValue value(BigDecimal value) {
        return new MCValue(currency, value);
    }

    public MCValue value(int value) {
        return new MCValue(currency, value);
    }

    public MCValue value(long value) {
        return new MCValue(currency, value);
    }

    public MCValue value(double value) {
        return new MCValue(currency, value);
    }

    @Transient
    public boolean isNative() {
        return currency == null;
    }

    /**
     * Performance consideration:
     *
     * 1, Max digits hold by a long: 9,223,372,036,854,775,808 = 19 digits.
     *
     * 2, For 10000-grouped (in a 16-bit word), a long contains 4*4 = 16 digits.
     *
     * 3, Keep in mind that expand the scale is always easier then shrinks.
     */
    @Column(scale = MONEY_ITEM_SCALE, precision = MONEY_ITEM_PRECISION, nullable = false)
    public BigDecimal getValue() {
        return value;
    }

    void setValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

    public final MCValue negate() {
        BigDecimal result = value.negate();
        return new MCValue(currency, result);
    }

    public final MCValue add(long num) {
        return add(BigDecimal.valueOf(num));
    }

    public final MCValue add(double num) {
        return add(BigDecimal.valueOf(num));
    }

    public final MCValue add(BigDecimal num) {
        BigDecimal result = value.add(num);
        return new MCValue(currency, result);
    }

    public final MCValue subtract(long num) {
        return subtract(BigDecimal.valueOf(num));
    }

    public final MCValue subtract(double num) {
        return subtract(BigDecimal.valueOf(num));
    }

    public final MCValue subtract(BigDecimal num) {
        BigDecimal result = value.subtract(num);
        return new MCValue(currency, result);
    }

    public final MCValue multiply(long num) {
        return multiply(BigDecimal.valueOf(num));
    }

    public final MCValue multiply(double num) {
        return multiply(BigDecimal.valueOf(num));
    }

    public final MCValue multiply(BigDecimal num) {
        BigDecimal result = value.multiply(num);
        return new MCValue(currency, result);
    }

    public final MCValue divide(long num) {
        return divide(BigDecimal.valueOf(num));
    }

    public final MCValue divide(double num) {
        return divide(BigDecimal.valueOf(num));
    }

    public final MCValue divide(BigDecimal num) {
        BigDecimal result = value.divide(num);
        return new MCValue(currency, result);
    }

    /**
     * Add, or fallback to native currency.
     *
     * @return Non-<code>null</code> addition result.
     */
    public final MCValue addFTN(IFxrProvider fxrProvider, MCValue other)
            throws FxrQueryException {
        if (Nullables.equals(currency, other.currency)) {
            BigDecimal sum = value.add(other.value);
            return new MCValue(currency, sum);
        }
        BigDecimal a = this.getNativeValue(fxrProvider);
        BigDecimal b = other.getNativeValue(fxrProvider);
        BigDecimal sum = a.add(b);
        return new MCValue(null, sum);
    }

    /**
     * Add, or fallback to native currency.
     *
     * @return Non-<code>null</code> addition result.
     */
    public final MCValue addFTN(IFxrProvider fxrProvider, MCValue... others)
            throws FxrQueryException {

        assert currency != null; // To simplify, null is just not supported here.

        boolean same = true;
        for (MCValue other : others) {
            if (!currency.equals(other.currency)) {
                same = false;
                break;
            }
        }

        BigDecimal sum;
        if (same) {
            sum = value;
            for (MCValue other : others)
                sum = sum.add(other.value);
        } else {
            sum = this.getNativeValue(fxrProvider);
            for (MCValue other : others) {
                BigDecimal n = other.getNativeValue(fxrProvider);
                sum = sum.add(n);
            }
        }

        return new MCValue(CurrencyConfig.NATIVE, sum);
    }

    public MCValue toNative(IFxrProvider fxrProvider)
            throws FxrQueryException {
        if (currency == null)
            return this;

        if (currency.equals(CurrencyConfig.NATIVE))
            return this;

        BigDecimal nativeValue = getNativeValue(fxrProvider);
        MCValue _native = new MCValue(CurrencyConfig.NATIVE, nativeValue);
        return _native;
    }

    public BigDecimal getNativeValue(IFxrProvider fxrProvider)
            throws FxrQueryException {
        if (fxrProvider == null)
            throw new NullPointerException("fxrProvider");

        Float _fxr = fxrProvider.getLatestFxr(currency, FxrUsage.MIDDLE);
        if (_fxr == null)
            throw new FxrQueryException("The FXR for the specified quote currency is not defined: " + currency);

        BigDecimal fxr = BigDecimal.valueOf(_fxr);
        BigDecimal local = getValue().multiply(fxr);
        return local;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MCValue))
            return false;

        MCValue o = (MCValue) obj;

        if (!Nullables.equals(currency, o.currency))
            return false;

        if (!value.equals(o.value))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        if (currency != null)
            hash = currency.hashCode();

        hash += value.hashCode();

        return hash;
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + "$ " + value;
    }

}
