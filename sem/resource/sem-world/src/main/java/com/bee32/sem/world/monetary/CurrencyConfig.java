package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Locale;

public class CurrencyConfig {

    public static final Currency NATIVE;

    static {
        // DEFAULT = ICurrencyAware.CNY;
        Locale nativeLocale = Locale.getDefault();
        NATIVE = Currency.getInstance(nativeLocale);
    }

}
