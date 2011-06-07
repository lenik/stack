package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.sem.calendar.ICalendarEvent;
import com.bee32.sem.calendar.ICalendarSource;

@Component
@Lazy
public class ChanceCalendarSource
        extends EnterpriseService
        implements ICalendarSource {

    CommonDataManager cdm;

    @Override
    public List<ICalendarEvent> getEvents(Date begin, Date end) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ICalendarEvent.class);
        detachedCriteria.add(Property.forName("beginTime").between(begin, end));
        return cdm.findByCriteria(detachedCriteria);
    }

}
