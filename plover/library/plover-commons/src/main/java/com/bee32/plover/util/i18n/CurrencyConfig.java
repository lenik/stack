package com.bee32.plover.util.i18n;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CurrencyConfig
        implements ILocaleAware {

    private static Currency nativeCurrency;

    static {
        // DEFAULT = ICurrencyAware.CNY;
        Locale nativeLocale = Locale.getDefault();
        nativeCurrency = Currency.getInstance(nativeLocale);

        // XXX - Force set to CNY.
        nativeCurrency = ICurrencyAware.CNY;
    }

    public static Currency getNative() {
        return nativeCurrency;
    }

    public static void setNative(Currency _nativeCurrency) {
        if (_nativeCurrency == null)
            throw new NullPointerException("_nativeCurrency");
        nativeCurrency = _nativeCurrency;
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
