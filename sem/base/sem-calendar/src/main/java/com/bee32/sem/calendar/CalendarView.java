package com.bee32.sem.calendar;

import java.util.List;

public class CalendarView {

    List<ICalendarEvent> events;

    public List<ICalendarEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ICalendarEvent> events) {
        this.events = events;
    }

}