package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.ox1.color.MomentIntervalCriteria;
import com.bee32.sem.calendar.ICalendarSource;

/**
 * 销售机会的日历源
 *
 * <p lang="en">
 */
public class ChanceCalendarSource
        extends DataService
        implements ICalendarSource {

    @Override
    public List<ChanceAction> getEvents(Date begin, Date end) {
        return DATA(ChanceAction.class).list(MomentIntervalCriteria.timeBetween(begin, end));
    }

}
