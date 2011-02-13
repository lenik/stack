package com.bee32.plover.conf.style;

import java.text.DateFormat;
import java.text.NumberFormat;

import com.bee32.plover.conf.IConf;

public interface ILocalePreference
        extends IConf {

    NumberFormat getCurrencyFormat();

    NumberFormat getIntegerFormat();

    NumberFormat getFloatFormat();

    NumberFormat getDoubleFormat();

    DateFormat getDateFormat();

    DateFormat getLongDateFormat();

    DateFormat getTimeFormat();

    DateFormat getLongTimeFormat();

    DateFormat getDateTimeFormat();

    DateFormat getLongDateTimeFormat();

}
