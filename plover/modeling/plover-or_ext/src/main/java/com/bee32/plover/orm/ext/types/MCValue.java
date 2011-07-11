package com.bee32.plover.orm.ext.types;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

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

    Currency currency = DEFAULT_CURRENCY;
    BigDecimal value;

    public MCValue() {
        value = new BigDecimal(0);
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
            currency = null;
        else {
            Currency currency = Currency.getInstance(currencyCode); // Already throws IAE, though.
            if (currency == null)
                throw new IllegalArgumentException("Bad currency code: " + currencyCode);
            this.currency = currency;
        }
    }

    /**
     * @return <code>null</code> for native currency.
     */
    @Transient
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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
    @Column(scale = 16, precision = 4, nullable = false)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
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

}
