package com.bee32.plover.util.i18n;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

public final class SolarDate
        implements Serializable, ITimeZoneAware {

    private static final long serialVersionUID = 1L;

    Date internal;

    SolarDate(long internalTime) {
        this.internal = new Date(internalTime);
    }

    public Date toDate() {
        return toDate(TimeZoneConfig.getNative());
    }

    public Date toDate(TimeZone timeZone) {

        return null;
    }

    public static SolarDate fromDate(Date date) {
        return fromDate(date, TimeZoneConfig.getNative());
    }

    public static SolarDate fromDate(Date date, TimeZone timeZone) {
        long userTime = date.getTime();
        long internalTime = userTime - timeZone.getOffset(userTime);
        return new SolarDate(internalTime);
    }

}
