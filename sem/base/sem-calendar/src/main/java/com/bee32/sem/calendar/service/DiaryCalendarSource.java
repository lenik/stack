package com.bee32.sem.calendar.service;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.sem.calendar.ICalendarEvent;
import com.bee32.sem.calendar.ICalendarSource;

@Service
@Lazy
public class DiaryCalendarSource
        implements ICalendarSource {

    @Override
    public List<ICalendarEvent> getEvents(Date begin, Date end) {
        return null;
    }

}
