package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CurrencyNames {

    static final Map<Locale, Map<String, Currency>> localRevMap;
    static {
        localRevMap = new HashMap<Locale, Map<String, Currency>>();
    }

    public static synchronized Map<String, Currency> getRevMap(Locale locale) {
        if (locale == null)
            throw new NullPointerException("locale");

        Map<String, Currency> revMap = localRevMap.get(locale);
        if (revMap == null) {
            revMap = buildRevMap(locale);
            localRevMap.put(locale, revMap);
        }
        return revMap;
    }

    static Map<String, Currency> buildRevMap(Locale locale) {
        Map<String, Currency> revMap = new HashMap<String, Currency>();
        for (Currency currency : Currency.getAvailableCurrencies()) {
            String displayName = currency.getDisplayName(locale);
            revMap.put(displayName, currency);
        }
        return revMap;
    }

}
