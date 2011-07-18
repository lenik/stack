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

    public MCValue(Currency currency, int amount) {
        this(currency, new BigDecimal(amount, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, long amount) {
        this(currency, new BigDecimal(amount, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, double amount) {
        this(currency, new BigDecimal(amount, DecimalConfig.MONEY_ITEM_CONTEXT));
    }

    public MCValue(Currency currency, BigDecimal amount) {
        if (amount == null)
            throw new NullPointerException("amount");
        this.currency = currency;
        this.value = amount;
    }

    @Column(name = "currency", length = 3, nullable = false)
    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    public void setCurrencyCode(String currencyCode) {
        if (currencyCode == null)
            setCurrency(null);
        else {
            Currency currency = Currency.getInstance(currencyCode); // Already throws IAE, though.
            if (currency == null)
                throw new IllegalArgumentException("Bad currency code: " + currencyCode);
            setCurrency(currency);
        }
    }

    /**
     * Get the currency.
     *
     * @return <code>null</code> for native currency.
     */
    @Transient
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Set the currency.
     *
     * @param currency
     *            Set to <code>null</code> for native currency.
     */
    void setCurrency(Currency currency) {
        this.currency = currency;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MCValue))
            return false;

        MCValue o = (MCValue) obj;

        if (!Nullables.equals(getCurrency(), o.getCurrency()))
            return false;

        if (!getValue().equals(o.getValue()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        if (getCurrency() != null)
            hash = getCurrency().hashCode();

        hash += getValue().hashCode();

        return hash;
    }

}
