package com.bee32.sem.calendar;

import java.util.Date;
import java.util.List;

public interface ICalendarEvent {

    Date getBeginDate();

    Date getEndDate();

    String getSubject();

    String getContent();

    List<String> getStyles();

}
