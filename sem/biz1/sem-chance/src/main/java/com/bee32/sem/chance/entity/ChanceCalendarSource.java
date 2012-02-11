package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.ox1.color.MomentIntervalCriteria;
import com.bee32.sem.calendar.ICalendarSource;

public class ChanceCalendarSource
        extends DataService
        implements ICalendarSource {

    @Override
    public List<ChanceAction> getEvents(Date begin, Date end) {
        return ctx.data.access(ChanceAction.class).list(MomentIntervalCriteria.timeBetween(begin, end));
    }

}
