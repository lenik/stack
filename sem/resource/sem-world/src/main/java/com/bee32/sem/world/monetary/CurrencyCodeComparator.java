package com.bee32.sem.world.monetary;

import java.util.Currency;

import javax.free.AbstractNonNullComparator;

public class CurrencyCodeComparator
        extends AbstractNonNullComparator<Currency> {

    @Override
    public int compareNonNull(Currency c1, Currency c2) {
        String code1 = c1.getCurrencyCode();
        String code2 = c2.getCurrencyCode();
        int cmp = code1.compareTo(code2);
        return cmp;
    }

    public static final CurrencyCodeComparator INSTANCE = new CurrencyCodeComparator();
}
