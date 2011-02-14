package com.bee32.plover.commons.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public interface LocalePreferenceDefaults {

    NumberFormat DEFAULT_CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    NumberFormat DEFAULT_INTEGER_FORMAT = NumberFormat.getIntegerInstance();
    NumberFormat DEFAULT_NUMBER_FORMAT = NumberFormat.getNumberInstance();
    NumberFormat DEFAULT_PERCENT_FORMAT = NumberFormat.getPercentInstance();

    DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
    DateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    DateFormat DEFAULT_LONG_DATE_FORMAT = DateFormat.getDateInstance();
    DateFormat DEFAULT_LONG_TIME_FORMAT = DateFormat.getTimeInstance();
    DateFormat DEFAULT_LONG_DATETIME_FORMAT = DateFormat.getDateTimeInstance();

}
