package com.bee32.sem.event.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.entity.EventPriority;

@RequestMapping(EventPriorityController.PREFIX + "*")
public class EventPriorityController
        extends BasicEntityController<EventPriority, Integer, EventPriorityDto> {

    public static final String PREFIX = SEMEventModule.PREFIX + "priority/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EventPriorityDto dto) {
        tab.push(dto.getPriority());
        tab.push(dto.getName());
        tab.push(dto.getDisplayName());
        tab.push(dto.getDescription());
    }

    @Override
    protected void fillTemplate(EventPriorityDto dto) {
        dto.setName("");
        dto.setDisplayName("");
        dto.setPriority(0);
        dto.setDescription("");
    }

}
