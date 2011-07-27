package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CurrencyConfig
        implements ILocaleAware {

    public static final Currency NATIVE;

    static {
        // DEFAULT = ICurrencyAware.CNY;
        Locale nativeLocale = Locale.getDefault();
        NATIVE = Currency.getInstance(nativeLocale);
    }

    public static List<Currency> list() {
        return list(CurrencyOrder.CODE);
    }

    public static List<Currency> list(CurrencyOrder order) {
        return order.getCurrencies();
    }

    static final boolean showNum = false;

    public static String format(Currency currency, Locale locale) {
        int num = currency.getNumericCode();
        String code = currency.getCurrencyCode();
        // String symbol = currency.getSymbol(locale);
        String name = currency.getDisplayName(locale);

        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.append(' ');
        sb.append(name);

        if (showNum) {
            sb.append(" (iso=");
            sb.append(num);
            sb.append(")");
        }

        return sb.toString();
    }

    public static String format(Currency currency) {
        Locale locale = NATIVE_LOCALE;
        return format(currency, locale);
    }

}
