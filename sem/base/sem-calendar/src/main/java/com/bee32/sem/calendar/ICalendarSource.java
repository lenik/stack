package com.bee32.sem.calendar;

import java.util.Date;
import java.util.List;

public interface ICalendarSource {

    // ICalendarSource
    // getEvents(begin, end) => event*

    List<ICalendarEvent> getEvents(Date begin, Date end);

}
