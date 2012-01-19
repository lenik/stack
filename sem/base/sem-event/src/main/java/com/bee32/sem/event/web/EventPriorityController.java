package com.bee32.sem.event.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.dto.EventPriorityDto;
import com.bee32.sem.event.entity.EventPriority;

@RequestMapping(EventPriorityController.PREFIX + "/*")
public class EventPriorityController
        extends BasicEntityController<EventPriority, Integer, EventPriorityDto> {

    public static final String PREFIX = SEMEventModule.PREFIX + "/priority";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EventPriorityDto dto) {
        tab.push(dto.getPriority());
        tab.push(dto.getLabel());
        tab.push(dto.getDescription());
    }

}
