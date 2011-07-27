package com.bee32.sem.world.monetary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import javax.free.AbstractNonNullComparator;

public abstract class CurrencyOrder
        extends AbstractNonNullComparator<Currency> {

    final List<Currency> currencies;

    public CurrencyOrder() {
        currencies = new ArrayList<Currency>(Currency.getAvailableCurrencies());
        Collections.sort(currencies, this);
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    private static class NumericOrder
            extends CurrencyOrder {

        @Override
        public int compareNonNull(Currency o1, Currency o2) {
            int cmp = o1.getNumericCode() - o2.getNumericCode();
            return cmp;
        }

    }

    private static class CodeOrder
            extends CurrencyOrder {

        @Override
        public int compareNonNull(Currency o1, Currency o2) {
            int cmp = o1.getCurrencyCode().compareTo(o2.getCurrencyCode());
            return cmp;
        }

    }

    private static class NativeOrder
            extends CurrencyOrder {

        @Override
        public int compareNonNull(Currency o1, Currency o2) {
            String dn1 = o1.getDisplayName();
            String dn2 = o2.getDisplayName();
            int cmp = dn1.compareTo(dn2);
            return cmp;
        }

    }

    public static final CurrencyOrder NUMERIC = new NumericOrder();
    public static final CurrencyOrder CODE = new CodeOrder();
    public static final CurrencyOrder NATIVE = new NativeOrder();

}
