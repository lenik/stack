package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.calendar.ICalendarSource;
import com.bee32.sem.chance.util.ChanceCriteria;

@Component
@Lazy
public class ChanceCalendarSource
        extends DataService
        implements ICalendarSource {

    @Override
    public List<ChanceAction> getEvents(Date begin, Date end) {
        return asFor(ChanceAction.class).list(ChanceCriteria.beganWithin(begin, end));
    }

}
