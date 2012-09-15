package com.bee32.sem.salary.util;

import java.io.Serializable;
import java.util.List;

import com.bee32.sem.salary.dto.EventBonusDto;
import com.bee32.sem.salary.dto.SalaryElementDto;

public class IReportRowModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    List<SalaryElementDto> elements;
    List<EventBonusDto> events;

    public IReportRowModel(List<SalaryElementDto> elements, List<EventBonusDto> events) {
        this.elements = elements;
        this.events = events;
    }

    public List<SalaryElementDto> getElements() {
        return elements;
    }

    public void setElements(List<SalaryElementDto> elements) {
        this.elements = elements;
    }

    public List<EventBonusDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventBonusDto> events) {
        this.events = events;
    }

}
