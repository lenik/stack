package com.bee32.sem.calendar;

import java.util.Date;
import java.util.List;

public interface ICalendarEvent {

    Date getBeginTime();

    Date getEndTime();

    String getSubject();

    String getContent();

    List<String> getStyles();

}
