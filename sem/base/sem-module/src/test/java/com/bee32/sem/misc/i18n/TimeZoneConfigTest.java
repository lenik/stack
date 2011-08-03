package com.bee32.sem.misc.i18n;

import java.util.TimeZone;

import org.junit.Assert;

public class TimeZoneConfigTest
        extends Assert
        implements ILocaleAware {

    public static void main(String[] args) {
        for (TimeZone tz : TimeZoneConfig.list(TimeZoneOrder.ID)) {
            System.out.println(TimeZoneConfig.format(tz, zh_CN));
        }
    }

}
