package com.bee32.zebra.oa.calendar;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.tinylily.model.sea.AbstractTextParametric;
import com.tinylily.model.sea.QVariantMap;

public class CalendarForm
        extends AbstractTextParametric
        implements IZebraSiteAnchors {

    int year;
    int month;
    int day;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        year = map.getInt("year", year);
        month = map.getInt("month", month);
        day = map.getInt("day", day);
    }

}
