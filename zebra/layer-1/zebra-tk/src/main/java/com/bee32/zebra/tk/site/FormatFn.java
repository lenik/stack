package com.bee32.zebra.tk.site;

import net.bodz.bas.c.java.util.Dates;

public class FormatFn {

    public String formatDate(Object date) {
        if (date == null)
            return null;
        else
            return Dates.YYYY_MM_DD.format(date);
    }

}
