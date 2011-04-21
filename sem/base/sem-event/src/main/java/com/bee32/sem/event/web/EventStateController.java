package com.bee32.sem.event.web;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.dict.DictController;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.dao.EventStateDao;
import com.bee32.sem.event.entity.EventState;

@RequestMapping(EventStateController.PREFIX + "*")
public class EventStateController
        extends DictController<EventState, Integer> {

    public static final String PREFIX = SEMEventModule.PREFIX + "/state/";

    @Inject
    EventStateDao eventStateDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "事件状态");
    }

}
