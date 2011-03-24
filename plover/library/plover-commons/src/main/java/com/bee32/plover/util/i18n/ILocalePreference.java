package com.bee32.plover.util.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.bee32.plover.conf.IConf;

public interface ILocalePreference
        extends IConf {

    Locale getLocale();

    void setLocale(Locale locale);

    NumberFormat getCurrencyFormat();

    NumberFormat getIntegerFormat();

    NumberFormat getFloatFormat();

    NumberFormat getDoubleFormat();

    NumberFormat getPercentFormat();

    DateFormat getDateFormat();

    DateFormat getTimeFormat();

    DateFormat getDateTimeFormat();

    DateFormat getLongDateFormat();

    DateFormat getLongTimeFormat();

    DateFormat getLongDateTimeFormat();

}
