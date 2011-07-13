package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Locale;

public class CurrencyConfig {

    public static final Currency DEFAULT;

    static {
        // DEFAULT = ICurrencyAware.CNY;
        Locale defaultLocale = Locale.getDefault();
        DEFAULT = Currency.getInstance(defaultLocale);
    }

}
