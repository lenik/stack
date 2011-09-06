package com.bee32.plover.util.i18n;

import java.util.TimeZone;

import org.junit.Assert;

import com.bee32.plover.util.i18n.ILocaleAware;
import com.bee32.plover.util.i18n.TimeZoneConfig;
import com.bee32.plover.util.i18n.TimeZoneOrder;

public class TimeZoneConfigTest
        extends Assert
        implements ILocaleAware {

    public static void main(String[] args) {
        for (TimeZone tz : TimeZoneConfig.list(TimeZoneOrder.ID)) {
            System.out.println(TimeZoneConfig.format(tz, zh_CN));
        }
    }

}
