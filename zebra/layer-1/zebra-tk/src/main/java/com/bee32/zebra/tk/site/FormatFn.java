package com.bee32.zebra.tk.site;

import java.util.Date;

import org.joda.time.DateTime;

import net.bodz.bas.c.java.util.Dates;

public class FormatFn {

    public String formatDate(Object date) {
        if (date == null)
            return null;

        if (date instanceof Date)
            return Dates.YYYY_MM_DD.format(date);

        if (date instanceof DateTime) {
            DateTime dateTime = (DateTime) date;
            date = dateTime.toDate();
            return Dates.YYYY_MM_DD.format(date);
        }

        return date.toString();
    }

}
