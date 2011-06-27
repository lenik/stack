package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.calendar.ICalendarSource;

@Component
@Lazy
public class ChanceCalendarSource
        extends EnterpriseService
        implements ICalendarSource {

    @Override
    public List<ChanceAction> getEvents(Date begin, Date end) {
        Criterion between = Property.forName("beginTime").between(begin, end);
        return asFor(ChanceAction.class).list(between);
    }

}
