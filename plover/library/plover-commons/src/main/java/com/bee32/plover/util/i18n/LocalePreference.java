package com.bee32.plover.util.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.free.ContextLocal;
import javax.free.IContext;

import com.bee32.plover.conf.ContextConf;

/**
 * 区域设置
 *
 * <p lang="en">
 * Locale Preference
 */
public class LocalePreference
        extends ContextConf
        implements ILocalePreference {

    private static final long serialVersionUID = 1L;

    static ContextLocal<Locale> locale//
    = new ContextLocal<Locale>(LocalePreferenceDefaults.DEFAULT_LOCALE);

    static ContextLocal<NumberFormat> currencyFormat //
    = new ContextLocal<NumberFormat>(LocalePreferenceDefaults.DEFAULT_CURRENCY_FORMAT);

    static ContextLocal<NumberFormat> integerFormat //
    = new ContextLocal<NumberFormat>(LocalePreferenceDefaults.DEFAULT_INTEGER_FORMAT);

    static ContextLocal<NumberFormat> floatFormat //
    = new ContextLocal<NumberFormat>(LocalePreferenceDefaults.DEFAULT_NUMBER_FORMAT);

    static ContextLocal<NumberFormat> doubleFormat //
    = new ContextLocal<NumberFormat>(LocalePreferenceDefaults.DEFAULT_NUMBER_FORMAT);

    static ContextLocal<NumberFormat> percentFormat //
    = new ContextLocal<NumberFormat>(LocalePreferenceDefaults.DEFAULT_PERCENT_FORMAT);

    static ContextLocal<DateFormat> dateFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_DATE_FORMAT);

    static ContextLocal<DateFormat> timeFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_TIME_FORMAT);

    static ContextLocal<DateFormat> dateTimeFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_DATETIME_FORMAT);

    static ContextLocal<DateFormat> longDateFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_LONG_DATE_FORMAT);

    static ContextLocal<DateFormat> longTimeFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_LONG_TIME_FORMAT);

    static ContextLocal<DateFormat> longDateTimeFormat //
    = new ContextLocal<DateFormat>(LocalePreferenceDefaults.DEFAULT_LONG_DATETIME_FORMAT);

    public LocalePreference(IContext context) {
        super(context);
    }

    @Override
    public Locale getLocale() {
        return locale.get(context);
    }

    @Override
    public void setLocale(Locale locale) {
        LocalePreference.locale.set(context, locale);
    }

    @Override
    public NumberFormat getCurrencyFormat() {
        return currencyFormat.get(context);
    }

    @Override
    public NumberFormat getIntegerFormat() {
        return integerFormat.get(context);
    }

    @Override
    public NumberFormat getFloatFormat() {
        return floatFormat.get(context);
    }

    @Override
    public NumberFormat getDoubleFormat() {
        return doubleFormat.get(context);
    }

    @Override
    public NumberFormat getPercentFormat() {
        return percentFormat.get(context);
    }

    @Override
    public DateFormat getDateFormat() {
        return dateFormat.get(context);
    }

    @Override
    public DateFormat getTimeFormat() {
        return timeFormat.get(context);
    }

    @Override
    public DateFormat getDateTimeFormat() {
        return dateTimeFormat.get(context);
    }

    @Override
    public DateFormat getLongDateFormat() {
        return longDateFormat.get(context);
    }

    @Override
    public DateFormat getLongTimeFormat() {
        return longTimeFormat.get(context);
    }

    @Override
    public DateFormat getLongDateTimeFormat() {
        return longDateTimeFormat.get(context);
    }

}
