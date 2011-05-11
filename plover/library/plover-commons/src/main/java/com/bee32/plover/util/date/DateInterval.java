package com.bee32.plover.util.date;

import java.util.Date;
import java.util.TimeZone;

public abstract class DateInterval {

    /**
     * Inclusive.
     */
    public abstract Date floor(TimeZone timeZone, Date date);

    /**
     * Exclusive.
     */
    public abstract Date ceil(TimeZone timeZone, Date date);

}
