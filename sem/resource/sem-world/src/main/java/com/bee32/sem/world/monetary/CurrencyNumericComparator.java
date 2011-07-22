package com.bee32.sem.world.monetary;

import java.util.Currency;

import javax.free.AbstractNonNullComparator;

public class CurrencyNumericComparator
        extends AbstractNonNullComparator<Currency> {

    @Override
    public int compareNonNull(Currency c1, Currency c2) {
        int n1 = c1.getNumericCode();
        int n2 = c2.getNumericCode();
        int cmp = n1 - n2;
        return cmp;
    }

    public static final CurrencyNumericComparator INSTANCE = new CurrencyNumericComparator();

}
