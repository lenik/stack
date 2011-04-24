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
    protected void data_buildRow(DataTableDxo tab, Dto item) {
    }

    @Override
    protected void create_template(Dto dto) {
    }

    @Override
    protected void doUnmarshal(Dto dto, E entity) {
    }

}
