package com.bee32.sem.event.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.entity.Event;

@RequestMapping(EventController.PREFIX + "*")
public class EventController<E extends Event, Dto extends AbstractEventDto<E>>
        extends BasicEntityController<E, Long, Dto> {

    public static final String PREFIX = SEMEventModule.PREFIX + "event/";

    @Override
    protected void fillDataRow(DataTableDxo tab, Dto event) {
        tab.push(event.getCategory());
        tab.push(event.getPriority());
        tab.push(event.getState());

        tab.push(event.getActor().getDisplayName());

        tab.push(event.getSubject());
        tab.push(event.getMessage());
        tab.push(event.getBeginTime());
        tab.push(event.getEndTime());

        // push ref-entity url...
    }

    @Override
    protected void fillTemplate(Dto event) {
    }

    protected void fillEntity(E entity, Dto dto) {
        dto.unmarshalTo(entity);
    }

}
